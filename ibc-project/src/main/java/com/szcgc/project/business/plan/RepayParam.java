package com.szcgc.project.business.plan;

import com.szcgc.project.constant.BusinessTypeEnum;
import com.szcgc.project.constant.RepayTypeEnum;
import lombok.Data;

import java.time.LocalDate;

/**
 * @Author liaohong
 * @create 2022/10/25 15:02
 */
@Data
public class RepayParam {
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
     * 业务品种
     */
    public BusinessTypeEnum businessType;

    /**
     * 放款金额(以分为单位)
     */
    public long amount;

    /**
     * 开始时间
     */
    public LocalDate beginDate;

    /**
     * 结束时间
     */
    public LocalDate endDate;

    /**
     * 还款方式
     */
    public RepayTypeEnum repayType;

    /**
     * 月还(以分为单位)
     */
    public long monthPay;

    /**
     * 还款日
     */
    public int repayDay;

    /**
     * 利息
     */
    public double interest;

    /**
     * 当月是否还本还息
     */
    public boolean currentMonthRepay;
}
