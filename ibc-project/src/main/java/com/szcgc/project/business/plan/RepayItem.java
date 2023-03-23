package com.szcgc.project.business.plan;

import lombok.Data;

import java.time.LocalDate;

/**
 * @Author liaohong
 * @create 2022/10/25 15:56
 */
@Data
public class RepayItem {

    /**
     * 项目Id
     */
    public int projectId;

    /**
     * 客户Id
     */
    public int customerId;

    /**
     * 放款Id
     */
    public int loanId;

    /**
     * 还款日期
     */
    public LocalDate repayDate;

    /**
     * 本次还款日距离上次还款日的天数
     */
    public int days;

    /**
     * 剩余本金
     */
    public long amount;

    /**
     * 还款本金
     */
    public long principalAmt;

    /**
     * 还款利息
     */
    public long interestAmt;

    //public long serviceAmt; // 服务费
    //public long taskAmt; // 任务折算
    // public String subject; // 科目

//        public long total() {
//            return principalAmt + interestAmt + serviceAmt;
//        }
}
