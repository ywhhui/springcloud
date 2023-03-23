package com.szcgc.project.repository;

import com.szcgc.project.model.RepayPlan;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * 还款计划数据交互类
 *
 * @author chenjiaming
 * @date 2022-10-24 08:52:54
 */
public interface RepayPlanRepository extends PagingAndSortingRepository<RepayPlan, Integer> {

    List<RepayPlan> findByProjectId(int projectId);

    List<RepayPlan> findByLoanId(Integer loanId);

    void deleteByLoanIdAndCreditedAndPeriodGreaterThan(Integer loanId, boolean b, Integer lastPeriod);
}
