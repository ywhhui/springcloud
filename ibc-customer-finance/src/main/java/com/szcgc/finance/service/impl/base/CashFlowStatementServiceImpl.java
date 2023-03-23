package com.szcgc.finance.service.impl.base;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.szcgc.comm.service.BaseService;
import com.szcgc.customer.feign.CustomerClient;
import com.szcgc.customer.model.CustomerInfo;
import com.szcgc.finance.model.base.CashFlowStatement;
import com.szcgc.finance.repository.base.CashFlowStatementRepository;
import com.szcgc.finance.service.base.CashFlowStatementService;
import com.szcgc.finance.vo.FinanceCustomerVo;
import com.szcgc.finance.vo.FinanceProjectVo;
import com.szcgc.project.feign.IProjectClient;
import com.szcgc.project.model.ProjectInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 现金流量表业务实现类
 *
 * @author chenjiaming
 * @date 2022-9-23 09:22:58
 */
@Slf4j
@Service
public class CashFlowStatementServiceImpl extends BaseService<CashFlowStatementRepository, CashFlowStatement, Integer> implements CashFlowStatementService {

    @Resource
    private CustomerClient customerClient;

    @Resource
    private IProjectClient projectClient;

    @Override
    public List<FinanceCustomerVo> search(String keyword) {
        List<Integer> custIds = Lists.newArrayList();
        Iterable<CashFlowStatement> iterable = repository.findAll();
        if (iterable == null) {
            log.error("获取所有现金流量表数据失败");
            return Lists.newArrayList();
        }
        // 遍历迭代对象将所有客户id添加到集合中
        iterable.forEach(obj -> custIds.add(obj.getCustomerId()));

        // 根据关键字和有财务数据的客户id集合获取客户信息
        List<CustomerInfo> customerInfos = customerClient.searchCust(keyword, custIds);

        // 将客户信息转换成前端需要的格式
        List<FinanceCustomerVo> financeCustomerVoList = customerInfos.stream().map(obj -> {
            FinanceCustomerVo financeCustomerVo = FinanceCustomerVo.builder().id(obj.getId()).name(obj.getName()).build();
            // 以项目id为key
            Map<Integer, List<CashFlowStatement>> map = Maps.newHashMap();
            // 遍历迭代器去添加map,减少查库次数
            iterable.forEach(it -> {
                if (it.getCustomerId().intValue() == financeCustomerVo.getId().intValue()) {
                    if (!map.containsKey(it.getProjectId())) {
                        map.put(it.getProjectId(), Lists.newArrayList());
                    }
                    map.get(it.getProjectId()).add(it);
                }
            });

            List<FinanceProjectVo> projectList = Lists.newArrayList();
            // 遍历map获取项目编码并封装数据
            for (Map.Entry<Integer, List<CashFlowStatement>> entry : map.entrySet()) {
                ProjectInfo projectInfo = projectClient.findById(entry.getKey());
                if (ObjectUtil.isNull(projectInfo)) {
                    continue;
                }
                FinanceProjectVo financeProjectVo = FinanceProjectVo.builder()
                        .id(projectInfo.getId())
                        .code(projectInfo.getCode())
                        .cashFlowStatementList(entry.getValue())
                        .build();
                projectList.add(financeProjectVo);
            }
            financeCustomerVo.setProjectList(projectList);

            return financeCustomerVo;
        }).collect(Collectors.toList());

        return financeCustomerVoList;
    }

    @Override
    public List<CashFlowStatement> list(Integer custId, Integer projectId) {
        if (ObjectUtil.isNotNull(projectId)) {
            return repository.findByCustomerId(custId);
        }

        return repository.findByCustomerId(custId);
    }

    @Override
    public CashFlowStatement findByProjectIdAndCustomerIdAndDate(Integer projectId, Integer custId, String date) {
        return repository.findTop1ByProjectIdAndCustomerIdAndDateOrderByUpdateAtDesc(projectId, custId, date);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void historyInsert(int accountId, List<Integer> ids, Integer projectId, Integer customerId) {
        List<CashFlowStatement> cashFlowStatements = Lists.newArrayList();
        // 根据id获取现金流量表数据
        Iterable<CashFlowStatement> iterable = repository.findAllById(ids);
        if (CollectionUtil.isEmpty(iterable)) {
            log.error("根据id获取所有现金流量表数据失败");
            return;
        }
        // 查出来后拷贝信息添加到list中,不做这一步操作,设置id为null保存会报错,百度说是什么查出来的实体类有缓存导致报错
        iterable.forEach(obj -> {
            CashFlowStatement cashFlowStatement = CashFlowStatement.builder().build();
            BeanUtil.copyProperties(obj, cashFlowStatement);
            cashFlowStatements.add(cashFlowStatement);
        });
        // 时间转时间戳排序
        cashFlowStatements.sort(Comparator.comparingLong(obj -> obj.getCreateAt().toInstant(ZoneOffset.ofHours(8)).toEpochMilli()));
        // 去重同一期次的，由于按创建时间排序过,如果有同一期次的将会新创建的覆盖旧的
        Map<String, CashFlowStatement> map = cashFlowStatements.stream().collect(Collectors.toMap(CashFlowStatement::getDate, obj -> obj, (v1, v2) -> v2));
        // 设置项目、客户id，创建人修改人id
        Lists.newArrayList(map.values()).forEach(obj -> {
            obj.setId(null);
            obj.setCustomerId(customerId);
            obj.setProjectId(projectId);
            obj.setCreateBy(accountId);
            obj.setUpdateBy(accountId);

            // 按项目id,客户id以及日期查是否已存在数据
            CashFlowStatement existsCashFlowStatement = findByProjectIdAndCustomerIdAndDate(projectId, customerId, obj.getDate());
            if (ObjectUtil.isNotNull(existsCashFlowStatement)) {
                // 已存在变为修改
                obj.setId(existsCashFlowStatement.getId());
            }
        });
        repository.saveAll(cashFlowStatements);
    }

    @Override
    public List<CashFlowStatement> calcValue(List<CashFlowStatement> cashFlowStatementList) {
        cashFlowStatementList.forEach(obj -> {
            // c04=sum(c01:c03)
            obj.setC04(NumberUtil.add(obj.getC01(), obj.getC02(), obj.getC03()));
            // c09=sum(c05:c08)
            obj.setC09(NumberUtil.add(obj.getC05(), obj.getC06(), obj.getC07(), obj.getC08()));
            // c10 = c04 - c09
            obj.setC10(NumberUtil.sub(obj.getC04(), obj.getC09()));
            // c15=sum(c11:c14)
            obj.setC15(NumberUtil.add(obj.getC11(), obj.getC12(), obj.getC13(), obj.getC14()));
            // c19=sum(c16:c18)
            obj.setC19(NumberUtil.add(obj.getC16(), obj.getC17(), obj.getC18()));
            // c20 = c15 - c19
            obj.setC20(NumberUtil.sub(obj.getC15(), obj.getC19()));
            // c25=sum(c21:c24)
            obj.setC25(NumberUtil.add(obj.getC21(), obj.getC22(), obj.getC23(), obj.getC24()));
            // c32=sum(c26:c31)
            obj.setC32(NumberUtil.add(obj.getC26(), obj.getC27(), obj.getC28(), obj.getC29(), obj.getC30(), obj.getC31()));
            // c33 = c25 - c32
            obj.setC33(NumberUtil.sub(obj.getC25(), obj.getC32()));
            // c35 = c10 + c20 + c33 + c34
            obj.setC35(NumberUtil.add(obj.getC10(), obj.getC20(), obj.getC33(), obj.getC34()));
            // c56=sum(c39:c55)
            obj.setC56(NumberUtil.add(obj.getC39(), obj.getC40(), obj.getC41(), obj.getC42(), obj.getC43(), obj.getC44(), obj.getC45(), obj.getC46(), obj.getC47(),
                    obj.getC48(), obj.getC49(), obj.getC50(), obj.getC51(), obj.getC52(), obj.getC53(), obj.getC54(), obj.getC55()));
            // c61=c57-c58+c59-c60
            obj.setC61(NumberUtil.sub(NumberUtil.add(obj.getC57(), obj.getC59()), obj.getC58(), obj.getC60()));
        });

        return cashFlowStatementList;
    }

}
