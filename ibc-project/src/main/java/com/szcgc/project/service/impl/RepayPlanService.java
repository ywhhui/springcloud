package com.szcgc.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.szcgc.account.feign.IAccountClient;
import com.szcgc.account.model.AccountInfo;
import com.szcgc.comm.service.BaseService;
import com.szcgc.customer.feign.CustomerClient;
import com.szcgc.customer.model.CustomerInfo;
import com.szcgc.project.model.ProjectInfo;
import com.szcgc.project.model.ProjectSupervisorInfo;
import com.szcgc.project.model.RepayPlan;
import com.szcgc.project.repository.ProjectRepository;
import com.szcgc.project.repository.RepayPlanRepository;
import com.szcgc.project.service.IRepayPlanService;
import com.szcgc.project.vo.repay.RepayBaseInfoVo;
import com.szcgc.project.vo.repay.RepayPlanVo;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class RepayPlanService extends BaseService<RepayPlanRepository, RepayPlan, Integer> implements IRepayPlanService {

    private static final float ONE_HUNDRED = 100;

    @Resource
    private CustomerClient customerClient;

    @Resource
    private ProjectRepository projectRepository;

    @Resource
    private ProjectSupervisorService projectSupervisorService;

    @Resource
    private IAccountClient accountClient;

    @Override
    public List<RepayPlanVo> list(Integer projectId, Integer loanId) {
        List<RepayPlan> list;
        if (ObjectUtil.isNull(loanId) || loanId == 0) {
            list = repository.findByProjectId(projectId);
        } else {
            list = repository.findByLoanId(loanId);
        }

        if (CollectionUtil.isEmpty(list)) {
            return Lists.newArrayList();
        }

        // 转换成vo
        return tranList(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String change(List<RepayPlan> list, int accountId) {
        if (CollectionUtil.isNotEmpty(list)) {
            for (int i = 0, len = list.size(); i < len; i++) {
                Integer period = list.get(i).getPeriod();
                if (ObjectUtil.isNull(period) || period == 0) {
                    return "期次不能为空";
                }
            }
        }

        // 这里还需调用宏哥的生成还款计划
        list = createList(list);

        if (CollectionUtil.isEmpty(list)) {
            return "还款计划为空";
        }

        Integer loanId = list.get(0).getLoanId();
        // 获取已存在的数据
        List<RepayPlan> repayPlans = repository.findByLoanId(loanId);
        // 转成以期次为key的map
        Map<Integer, RepayPlan> existsMap = repayPlans.stream().collect(Collectors.toMap(RepayPlan::getPeriod, obj -> obj));
        // 根据期次排序
        list.sort(Comparator.comparingLong(RepayPlan::getPeriod));
        // 最后一个期次
        Integer lastPeriod = list.get(list.size() - 1).getPeriod();
        // 需要保存数据根据期次去重
        Map<Integer, RepayPlan> map = list.stream().collect(Collectors.toMap(RepayPlan::getPeriod, obj -> obj, (o1, o2) -> o2));

        // 已入账数据
        AtomicInteger creditedNum = new AtomicInteger(0);
        // 筛选需要新增的数据保留未入账的
        list = CollectionUtil.newArrayList(map.values()).stream().filter(obj -> {
            obj.setRealRepayAccrual(getNum(obj.getRealRepayAccrual()));
            obj.setRealRepayCapital(getNum(obj.getRealRepayCapital()));
            obj.setShouldRepayAccrual(getNum(obj.getShouldRepayAccrual()));
            obj.setShouldRepayCapital(getNum(obj.getShouldRepayCapital()));
            obj.setCredited(false);
            obj.setCreateBy(accountId);
            obj.setUpdateBy(accountId);

            // 已存在还款计划
            RepayPlan existsRepayPlan = existsMap.get(obj.getPeriod());
            if (ObjectUtil.isNotNull(existsMap)) {
                obj.setId(existsRepayPlan.getId());
                if (existsRepayPlan.getCredited()) {
                    creditedNum.getAndIncrement();
                    return false;
                }
            }

            return true;
        }).collect(Collectors.toList());

        // 已入账数据小于等于新账单数据
        if (creditedNum.get() <= list.size()) {
            return "还款计划数异常,请重新填写";
        }

        // 删除多余期次且未入账数据
        repository.deleteByLoanIdAndCreditedAndPeriodGreaterThan(loanId, false, lastPeriod);
        // 批量新增
        batchInsert(list);
        return "";
    }

    private List<RepayPlan> createList(List<RepayPlan> list) {
//        // 还款期次
//        int period = 12;
//        // 利率
//        int rate = 10;
//        // 还款日
//        Integer day = 20;
//        Date curDate = new Date();
//        Integer year = DateUtil.year(curDate);
//        Integer month = DateUtil.nextMonth().month();
//
//        // 还款计划
//        List<RepayPlan> repayPlans = Lists.newArrayList();
//        if (CollectionUtil.isEmpty(list)) {
//            return repayPlans;
//        }
//        RepayPlan repayPlan = repayPlans.get(0);
//        Integer projectId = repayPlan.getProjectId();
//        Integer custId = repayPlan.getCustomerId();
//        Integer loanId = repayPlan.getLoanId();
//        period = list.size();
//        // 贷款金额
//        BigDecimal num = new BigDecimal(500000);
//        BigDecimal residueRate = NumberUtil.mul(NumberUtil.div(num, 100), rate);
//
//        long rateNum = NumberUtil.mul(NumberUtil.div(num, 100), rate, 100).longValue();
//        long repayNum = NumberUtil.mul(NumberUtil.div(num, period), 100).longValue();
//        for (int i = 0; i < period; i++) {
//            RepayPlan existsRepayPlan = list.get(i);
//            if (existsRepayPlan.getCredited()) {
//                repayPlans.add(existsRepayPlan);
//                num = NumberUtil.sub(num, NumberUtil.div(new BigDecimal(existsRepayPlan.getShouldRepayCapital()), 100));
//                residueRate = NumberUtil.sub(residueRate, NumberUtil.div(new BigDecimal(existsRepayPlan.getShouldRepayAccrual()), 100));
//                Integer residuePeriod = period - i - 1;
//                repayNum = NumberUtil.mul(NumberUtil.div(num, residuePeriod), 100).longValue();
//                rateNum = NumberUtil.mul(NumberUtil.div(residueRate, residuePeriod), 100).longValue();
//                continue;
//            }
//            Calendar calendar = Calendar.getInstance();
//            calendar.set(year, month, day);
//            calendar.add(Calendar.MONTH, i);
//            String date = String.format("%s-%02d-%02d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
//            num = NumberUtil.sub(num, NumberUtil.div(repayNum, 100));
//            residueRate = NumberUtil.sub(residueRate, NumberUtil.div(rateNum, 100));
//            repayPlans.add(RepayPlan.builder()
//                    .projectId(projectId)
//                    .customerId(custId)
//                    .loanId(loanId)
//                    .shouldRepayDate(date)
//                    .shouldRepayCapital(repayNum)
//                    .shouldRepayAccrual(rateNum)
//                    .build());
//        }
//        return repayPlans;
        return Lists.newArrayList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String credited(List<RepayPlan> list) {
        if (CollectionUtil.isEmpty(list)) {
            return "入账数据集合不能为空";
        }
        // 获取已有数据
        List<RepayPlan> repayPlans = repository.findByLoanId(list.get(0).getLoanId());
        // 转成以期次为key
        Map<Integer, RepayPlan> map = repayPlans.stream().collect(Collectors.toMap(RepayPlan::getPeriod, obj -> obj));
        // 需要入账数据以期次排序
        list.sort(Comparator.comparingLong(RepayPlan::getPeriod));
        // 需要入账的第一个期次
        Integer firstPeriod = list.get(0).getPeriod();
        // 需要入账的最后一个期次
        Integer lastPeriod = list.get(list.size() - 1).getPeriod();
        // 需要入账的期次set
        Set<Integer> set = list.stream().map(RepayPlan::getPeriod).collect(Collectors.toSet());
        // 校验是否存在勾选的 1、2、4期 少勾选3期入账的情况
        for (int i = firstPeriod; i <= lastPeriod; i++) {
            if (!set.contains(i)) {
                return String.format("第%s期未入账,请勾选第%s期", i, i);
            }
        }

        // 校验是否存在2期未入账,直接跳过勾选3、4期进行入账的情况
        for (int i = 1; i < firstPeriod; i++) {
            // 已存在数据
            RepayPlan existsRepayPlan = map.get(i);
            if (!existsRepayPlan.getCredited()) {
                return String.format("第%s期未入账,请先入账第%s期", i, i);
            }
        }

        for (int i = 0, len = list.size(); i < len; i++) {
            // 需要入账的数据
            RepayPlan repayPlan = list.get(i);
            // 需要入账的期次
            Integer period = repayPlan.getPeriod();
            // 该期次已存在数据
            RepayPlan existsRepayPlan = map.get(period);
            // 该期次不存在数据
            if (ObjectUtil.isNull(existsRepayPlan)) {
                return String.format("第%s期为新增数据,请先变更账单", period);
            }

            // 该期次已入账
            if (existsRepayPlan.getCredited()) {
                return String.format("第%s期已入账,请勿重复入账", period);
            }
            // 判断应还数据是否改动
            boolean res = existsRepayPlan.getShouldRepayAccrual().longValue() != getNum(repayPlan.getShouldRepayAccrual()).longValue()
                    || existsRepayPlan.getShouldRepayCapital().longValue() != getNum(repayPlan.getShouldRepayCapital()).longValue()
                    || !existsRepayPlan.getShouldRepayDate().equals(repayPlan.getShouldRepayDate());
            if (res) {
                return String.format("第%s期数据已修改,请先变更账单", period);
            }

            res = getNum(repayPlan.getRealRepayAccrual()) == 0 && getNum(repayPlan.getRealRepayCapital()) == 0 || ObjectUtil.isNull(repayPlan.getRealRepayDate());
            if (res) {
                return String.format("第%s期,还款信息有误,请重新输入", period);
            }

            repayPlan.setId(existsRepayPlan.getId());
            repayPlan.setCredited(true);
        }

        // 数据入账
        batchInsert(list);

        return "";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String del(Integer id, Integer accountId) {
        RepayPlan repayPlan = find(id).orElse(new RepayPlan());
        if (ObjectUtil.isNull(repayPlan.getId())) {
            return "未找到该id对应的还款计划";
        }
        if (ObjectUtil.isNotNull(repayPlan.getCredited()) && repayPlan.getCredited()) {
            return "已入账不允许删除";
        }
        delete(id);
        // 变更还款计划
        change(repository.findByLoanId(repayPlan.getLoanId()), accountId);
        return "";
    }

    @Override
    public RepayBaseInfoVo baseInfo(Integer projectId, Integer loanId) {
        // 获取项目信息
        ProjectInfo projectInfo = projectRepository.findById(projectId).orElse(new ProjectInfo());
        // 获取客户信息
        CustomerInfo customerInfo = customerClient.findCustById(projectInfo.getCustomerId()).orElse(new CustomerInfo());
        // 获取项目经理信息
        ProjectSupervisorInfo projectSupervisorInfo = projectSupervisorService.findByProjectId(projectId);
        String roleA = projectSupervisorInfo.getRoleA();
        if (StrUtil.isBlank(roleA)) {
            AccountInfo accountInfo = accountClient.findAccount(projectSupervisorInfo.getRoleAId());
            if (ObjectUtil.isNotNull(accountInfo)) {
                roleA = accountInfo.getRealName();
            } else {
                roleA = "";
            }
        }
        // 还需要获取放款信息,等待相关feign开发完成

        RepayBaseInfoVo repayBaseInfoVo = RepayBaseInfoVo.builder()
                .projectId(projectId)
                .projectCode(projectInfo.getCode())
                .customerId(customerInfo.getId())
                .customerName(customerInfo.getName())
                .roleA(roleA)
                .build();

        return repayBaseInfoVo;
    }

    /**
     * 获取数字
     *
     * @param num 数字
     * @return
     */
    private Long getNum(Long num) {
        return ObjectUtil.defaultIfNull(num, 0L);
    }

    /**
     * 集合转换
     *
     * @param list
     * @return
     */
    private List<RepayPlanVo> tranList(List<RepayPlan> list) {
        if (CollectionUtil.isEmpty(list)) {
            return Lists.newArrayList();
        }
        return list.stream().map(obj -> {
            RepayPlanVo repayPlanVo = new RepayPlanVo();
            BeanUtil.copyProperties(obj, repayPlanVo);
            repayPlanVo.setRealRepayAccrual(tranUnit(obj.getRealRepayAccrual()));
            repayPlanVo.setRealRepayCapital(tranUnit(obj.getRealRepayCapital()));
            repayPlanVo.setShouldRepayAccrual(tranUnit(obj.getShouldRepayAccrual()));
            repayPlanVo.setShouldRepayCapital(tranUnit(obj.getShouldRepayCapital()));
            return repayPlanVo;
        }).collect(Collectors.toList());
    }

    /**
     * 转换单位为元
     *
     * @param num 单位为分的long类型数字
     * @return 转换后的对象
     */
    private BigDecimal tranUnit(Long num) {
        if (ObjectUtil.isNull(num)) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(NumberUtil.div(num.floatValue(), ONE_HUNDRED));
    }
}
