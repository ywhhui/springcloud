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
import com.szcgc.finance.model.base.IncomeStatement;
import com.szcgc.finance.repository.base.IncomeStatementRepository;
import com.szcgc.finance.service.base.IncomeStatementService;
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
 * 损益表业务实现类
 *
 * @author chenjiaming
 * @date 2022-9-23 09:22:58
 */
@Slf4j
@Service
public class IncomeStatementServiceImpl extends BaseService<IncomeStatementRepository, IncomeStatement, Integer> implements IncomeStatementService {

    @Resource
    private CustomerClient customerClient;

    @Resource
    private IProjectClient projectClient;

    @Override
    public List<FinanceCustomerVo> search(String keyword) {
        List<Integer> custIds = Lists.newArrayList();
        Iterable<IncomeStatement> iterable = repository.findAll();
        if (iterable == null) {
            log.error("获取所有损益表数据失败");
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
            Map<Integer, List<IncomeStatement>> map = Maps.newHashMap();
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
            for (Map.Entry<Integer, List<IncomeStatement>> entry : map.entrySet()) {
                ProjectInfo projectInfo = projectClient.findById(entry.getKey());
                if (ObjectUtil.isNull(projectInfo)) {
                    continue;
                }
                FinanceProjectVo financeProjectVo = FinanceProjectVo.builder()
                        .id(projectInfo.getId())
                        .code(projectInfo.getCode())
                        .incomeStatementList(entry.getValue())
                        .build();
                projectList.add(financeProjectVo);
            }
            financeCustomerVo.setProjectList(projectList);

            return financeCustomerVo;
        }).collect(Collectors.toList());

        return financeCustomerVoList;
    }

    @Override
    public List<IncomeStatement> list(Integer custId, Integer projectId) {
        if (ObjectUtil.isNotNull(projectId)) {
            return repository.findByCustomerId(custId);
        }

        return repository.findByCustomerId(custId);
    }

    @Override
    public IncomeStatement findByProjectIdAndCustomerIdAndDate(Integer projectId, Integer custId, String date) {
        return repository.findTop1ByProjectIdAndCustomerIdAndDateOrderByUpdateAtDesc(projectId, custId, date);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void historyInsert(int accountId, List<Integer> ids, Integer projectId, Integer customerId) {
        List<IncomeStatement> incomeStatements = Lists.newArrayList();
        // 根据id获取资产负债表数据
        Iterable<IncomeStatement> iterable = repository.findAllById(ids);
        if (CollectionUtil.isEmpty(iterable)) {
            log.error("根据id获取所有资产负债表数据失败");
            return;
        }
        // 查出来后拷贝信息添加到list中,不做这一步操作,设置id为null保存会报错,百度说是什么查出来的实体类有缓存导致报错
        iterable.forEach(obj -> {
            IncomeStatement incomeStatement = IncomeStatement.builder().build();
            BeanUtil.copyProperties(obj, incomeStatement);
            incomeStatements.add(incomeStatement);
        });
        // 时间转时间戳排序
        incomeStatements.sort(Comparator.comparingLong(obj -> obj.getCreateAt().toInstant(ZoneOffset.ofHours(8)).toEpochMilli()));
        // 去重同一期次的，由于按创建时间排序过,如果有同一期次的将会新创建的覆盖旧的
        Map<String, IncomeStatement> map = incomeStatements.stream().collect(Collectors.toMap(IncomeStatement::getDate, obj -> obj, (v1, v2) -> v2));
        // 设置项目、客户id，创建人修改人id
        Lists.newArrayList(map.values()).forEach(obj -> {
            obj.setId(null);
            obj.setCustomerId(customerId);
            obj.setProjectId(projectId);
            obj.setCreateBy(accountId);
            obj.setUpdateBy(accountId);

            // 按项目id,客户id以及日期查是否已存在数据
            IncomeStatement existsIncomeStatement = findByProjectIdAndCustomerIdAndDate(projectId, customerId, obj.getDate());
            if (ObjectUtil.isNotNull(existsIncomeStatement)) {
                // 已存在变为修改
                obj.setId(existsIncomeStatement.getId());
            }
        });
        repository.saveAll(incomeStatements);
    }

    @Override
    public List<IncomeStatement> calcValue(List<IncomeStatement> incomeStatementList) {
        incomeStatementList.forEach(obj -> {
            // b04=b01-b02-b03
            obj.setB04(NumberUtil.sub(obj.getB01(), obj.getB02(), obj.getB03()));
            // b1a=b04-b0a-b0c-b0e-b0g+b0i+b0k
            obj.setB1a(NumberUtil.add(NumberUtil.sub(obj.getB04(), obj.getB0a(), obj.getB0c(), obj.getB0e(), obj.getB0g()), obj.getB0i(), obj.getB0k()));
            // b2a=b1a+b1c+b1e+b1g-b1i
            obj.setB2a(NumberUtil.sub(NumberUtil.add(obj.getB1a(), obj.getB1c(), obj.getB1e(), obj.getB1g()), obj.getB1i()));
            // b3a=b2a-b2c-b2e-b2g
            obj.setB3a(NumberUtil.sub(obj.getB2a(), obj.getB2c(), obj.getB2e(), obj.getB2g()));
            // b3g=b3a+b3c+b3e
            obj.setB3g(NumberUtil.add(obj.getB3a(), obj.getB3c(), obj.getB3e()));
            // b3p=b3g-b3i-b3k-b3m
            obj.setB3p(NumberUtil.sub(obj.getB3g(), obj.getB3i(), obj.getB3k(), obj.getB3m()));
            // b3z=b3p-b3r-b3t-b3v-b3x
            obj.setB3z(NumberUtil.sub(obj.getB3p(), obj.getB3r(), obj.getB3t(), obj.getB3v(), obj.getB3x()));
            // b43=b3a-b41
            obj.setB43(NumberUtil.sub(obj.getB3a(), obj.getB41()));
            // b4i=b3a+b4g
            obj.setB4i(NumberUtil.add(obj.getB3a(), obj.getB4g()));
            // b4m=b4i-b4k
            obj.setB4m(NumberUtil.sub(obj.getB4i(), obj.getB4k()));
        });

        return incomeStatementList;
    }

}
