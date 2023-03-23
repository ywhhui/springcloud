package com.szcgc.project.service;

import com.szcgc.project.model.LoanApprovalInfo;
import com.szcgc.project.model.LoanData;

/**
 * @Author liaohong
 * @create 2020/10/20 10:38
 */
public interface ILoanApprovalService extends IProjectSuperFind<LoanApprovalInfo, Integer> {

    LoanData readData(int projectId);

    boolean withIssueYes(int projectId);

    long sumAmount(int projectId);

    long sumCustomerAmount(int customerId);
}
