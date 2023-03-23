package com.szcgc.project.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

/**
 * @Author liaohong
 * @create 2020/10/21 15:12
 * 放款数据
 */
public class LoanData {

    @Schema(description = "项目Id")
    private int projectId;

    @Schema(description = "累计放款金额")
    private long totalAmount;

    @Schema(description = "累计放款次数")
    private int times;

    @Schema(description = "放款银行")
    private int bankBranchId;

    @Schema(description = "开始日期")
    private LocalDate beginDate;

    @Schema(description = "结束日期")
    private LocalDate finishDate;

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getBankBranchId() {
        return bankBranchId;
    }

    public void setBankBranchId(int bankBranchId) {
        this.bankBranchId = bankBranchId;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }
}
