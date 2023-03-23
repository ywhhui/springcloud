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
import com.szcgc.finance.model.base.BalanceSheet;
import com.szcgc.finance.repository.base.BalanceSheetRepository;
import com.szcgc.finance.service.base.BalanceSheetService;
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
 * 资产负债表业务实现类
 *
 * @author chenjiaming
 * @date 2022-9-23 09:22:58
 */
@Slf4j
@Service
public class BalanceSheetServiceImpl extends BaseService<BalanceSheetRepository, BalanceSheet, Integer> implements BalanceSheetService {

    @Resource
    private CustomerClient customerClient;

    @Resource
    private IProjectClient projectClient;

    @Override
    public List<FinanceCustomerVo> search(String keyword) {
        List<Integer> custIds = Lists.newArrayList();
        Iterable<BalanceSheet> iterable = repository.findAll();
        if (iterable == null) {
            log.error("获取所有资产负债表数据失败");
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
            Map<Integer, List<BalanceSheet>> map = Maps.newHashMap();
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
            for (Map.Entry<Integer, List<BalanceSheet>> entry : map.entrySet()) {
                ProjectInfo projectInfo = projectClient.findById(entry.getKey());
                if (ObjectUtil.isNull(projectInfo)) {
                    continue;
                }
                FinanceProjectVo financeProjectVo = FinanceProjectVo.builder()
                        .id(projectInfo.getId())
                        .code(projectInfo.getCode())
                        .balanceSheetList(entry.getValue())
                        .build();
                projectList.add(financeProjectVo);
            }
            financeCustomerVo.setProjectList(projectList);

            return financeCustomerVo;
        }).collect(Collectors.toList());

        return financeCustomerVoList;
    }

    @Override
    public List<BalanceSheet> list(Integer custId, Integer projectId) {
        if (ObjectUtil.isNotNull(projectId)) {
            return repository.findByCustomerId(custId);
        }

        return repository.findByCustomerId(custId);
    }

    @Override
    public BalanceSheet findByProjectIdAndCustomerIdAndDate(Integer projectId, Integer custId, String date) {
        return repository.findTop1ByProjectIdAndCustomerIdAndDateOrderByUpdateAtDesc(projectId, custId, date);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void historyInsert(int accountId, List<Integer> ids, Integer projectId, Integer customerId) {
        List<BalanceSheet> balanceSheets = Lists.newArrayList();
        // 根据id获取资产负债表数据
        Iterable<BalanceSheet> iterable = repository.findAllById(ids);
        if (CollectionUtil.isEmpty(iterable)) {
            log.error("根据id获取所有资产负债表数据失败");
            return;
        }
        // 查出来后拷贝信息添加到list中,不做这一步操作,设置id为null保存会报错,百度说是什么查出来的实体类有缓存导致报错
        iterable.forEach(obj -> {
            BalanceSheet balanceSheet = BalanceSheet.builder().build();
            BeanUtil.copyProperties(obj, balanceSheet);
            balanceSheets.add(balanceSheet);
        });
        // 时间转时间戳排序
        balanceSheets.sort(Comparator.comparingLong(obj -> obj.getCreateAt().toInstant(ZoneOffset.ofHours(8)).toEpochMilli()));
        // 去重同一期次的，由于按创建时间排序过,如果有同一期次的将会新创建的覆盖旧的
        Map<String, BalanceSheet> map = balanceSheets.stream().collect(Collectors.toMap(BalanceSheet::getDate, obj -> obj, (v1, v2) -> v2));
        // 设置项目、客户id，创建人修改人id
        Lists.newArrayList(map.values()).forEach(obj -> {
            obj.setId(null);
            obj.setCustomerId(customerId);
            obj.setProjectId(projectId);
            obj.setCreateBy(accountId);
            obj.setUpdateBy(accountId);

            // 按项目id,客户id以及日期查是否已存在数据
            BalanceSheet existsBalanceSheet = findByProjectIdAndCustomerIdAndDate(projectId, customerId, obj.getDate());
            if (ObjectUtil.isNotNull(existsBalanceSheet)) {
                // 已存在变为修改
                obj.setId(existsBalanceSheet.getId());
            }
        });
        repository.saveAll(balanceSheets);
    }

    @Override
    public List<BalanceSheet> calcValue(List<BalanceSheet> balanceSheetList) {
        balanceSheetList.forEach(obj -> {
            // a0z=a01+a0a+a0c+a0e+a0g+a0i+a0k+a0m+a0p+a0r+a0t+a0v+a0w+a0x+a0y
            obj.setA0z(NumberUtil.add(obj.getA01(), obj.getA0a(), obj.getA0c(), obj.getA0e(), obj.getA0g(), obj.getA0i(), obj.getA0k(),
                    obj.getA0m(), obj.getA0p(), obj.getA0r(), obj.getA0t(), obj.getA0v(), obj.getA0w(), obj.getA0x(), obj.getA0y()));
            // a17=a14+a16
            obj.setA17(NumberUtil.add(obj.getA14(), obj.getA16()));
            // a19=a17-a18
            obj.setA19(NumberUtil.sub(obj.getA17(), obj.getA18()));
            // a1e=a1a-a1c
            obj.setA1e(NumberUtil.sub(obj.getA1a(), obj.getA1c()));
            // a1i=a1e-a1g
            obj.setA1i(NumberUtil.sub(obj.getA1e(), obj.getA1g()));
            // a1r=a1i+a1k+a1m+a1p
            obj.setA1r(NumberUtil.add(obj.getA1i(), obj.getA1k(), obj.getA1m(), obj.getA1p()));
            // a2r=a10+a11+a12+a13+a19+a1r+a2a+a2c+a2e+a2g+a2i+a2k+a2m+a2p
            obj.setA2r(NumberUtil.add(obj.getA10(), obj.getA11(), obj.getA12(), obj.getA13(), obj.getA19(), obj.getA1r(), obj.getA2a(), obj.getA2c(), obj.getA2e(),
                    obj.getA2g(), obj.getA2i(), obj.getA2k(), obj.getA2m(), obj.getA2p()));
            // a2z=a0z+a2r
            obj.setA2z(NumberUtil.add(obj.getA0z(), obj.getA2r()));
            // a3z=a31+a33+a35+a37+a39+a3b+a3d+a3f+a3h+a3j+a3m+a3p+a3r+a3t+a3v
            obj.setA3z(NumberUtil.add(obj.getA31(), obj.getA33(), obj.getA35(), obj.getA37(), obj.getA39(), obj.getA3b(), obj.getA3d(), obj.getA3f(), obj.getA3h(),
                    obj.getA3j(), obj.getA3m(), obj.getA3p(), obj.getA3r(), obj.getA3t(), obj.getA3v()));
            // a4j=a41+a43+a45+a4a+a4c+a4e+a4g
            obj.setA4j(NumberUtil.add(obj.getA41(), obj.getA43(), obj.getA45(), obj.getA4a(), obj.getA4c(), obj.getA4e(), obj.getA4g()));
            // a4z=a3z+a4j
            obj.setA4z(NumberUtil.add(obj.getA3z(), obj.getA4j()));
            // a5h=a51+a53+a57+a5b+a5d+a5f
            obj.setA5h(NumberUtil.add(obj.getA51(), obj.getA53(), obj.getA57(), obj.getA5b(), obj.getA5d(), obj.getA5f()));
            // a5x=a5h+a5j
            obj.setA5x(NumberUtil.add(obj.getA5h(), obj.getA5j()));
            // a5z=a4z+a5x
            obj.setA5z(NumberUtil.add(obj.getA4z(), obj.getA5x()));
        });

        return balanceSheetList;
    }
}
