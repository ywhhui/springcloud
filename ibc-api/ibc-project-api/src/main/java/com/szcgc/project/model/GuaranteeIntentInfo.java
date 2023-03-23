package com.szcgc.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.szcgc.project.constant.RepayTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author liaohong
 * @create 2020/10/15 14:45
 * 担保意向书(本质也是项目签约信息)
 * 父类中的amount during businessType都是银行回复数据
 */
@Entity
@Table(name = "guaranteeintentinfo", schema = "gmis_project")
public class GuaranteeIntentInfo extends ProjectSuperInfo {

    public static final int MAX_FAIL_TIME = 2;
    private static final int EXPIRE_MONTH = 3;
    private static final int ISSUE_YES = 1;
    private static final int ISSUE_NO = 2;
    private static final int ENROLL_YES = 1;
    public static final int ENROLL_NO = 2;

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "评审结论Id")
    //@Column(updatable = false)   //可能重新上会
    private int evaluateId;  //由此控制meetingDate等字段

    @Schema(description = "会议日期")
    //@Column(updatable = false)    //可能重新上会
    private LocalDate meetingDate;

    @Schema(description = "有效日期")
    //@Column(updatable = false)    //可能重新上会
    private LocalDate expireDate;

    @Schema(description = "还款方式")
    @Enumerated(EnumType.STRING)
    //@Column(updatable = false, length = 50)
    @Column(length = 50)
    private RepayTypeEnum repayType;

    @Schema(description = "定期还款金额")
    //@Column(updatable = false)
    private long repayAmt;

    @Schema(description = "银行支行Id")
    private int bankBranchId;

    @Schema(description = "申请人")
    private int applyBy;

    @Schema(description = "申请时间")
    @JsonIgnore
    private LocalDateTime applyAt;

    @Schema(description = "签发人")
    private int issueBy;

    @Schema(description = "签发意见(1:同意,2:不同意)")
    private int issueRst;

    @Schema(description = "签发时间")
    private LocalDate issueDate;

    @Schema(description = "签发操作时间")
    @JsonIgnore
    private LocalDateTime issueAt;

    @Schema(description = "登记人")
    private int enrollBy;

    @Schema(description = "银行回复意见(1:同意,2:不同意)")
    private int enrollRst;

    @Schema(description = "银行回复时间")
    private LocalDate enrollDate;

    @Schema(description = "登记操作时间")
    @JsonIgnore
    private LocalDateTime enrollAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEvaluateId() {
        return evaluateId;
    }

    public void setEvaluateId(int evaluateId) {
        this.evaluateId = evaluateId;
    }

    public LocalDate getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(LocalDate meetingDate) {
        if (meetingDate == null) {
            meetingDate = LocalDate.now();
        }
        this.meetingDate = meetingDate;
        this.expireDate = meetingDate.plusMonths(EXPIRE_MONTH);
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    public RepayTypeEnum getRepayType() {
        return repayType;
    }

    public void setRepayType(RepayTypeEnum repayType) {
        this.repayType = repayType;
    }

    public long getRepayAmt() {
        return repayAmt;
    }

    public void setRepayAmt(long repayAmt) {
        this.repayAmt = repayAmt;
    }

    public int getBankBranchId() {
        return bankBranchId;
    }

    public void setBankBranchId(int bankBranchId) {
        this.bankBranchId = bankBranchId;
    }

    public int getIssueRst() {
        return issueRst;
    }

    public void setIssueRst(int issueRst) {
        if (issueRst != ISSUE_YES && issueRst != ISSUE_NO)
            throw new RuntimeException("error param" + issueRst);
        this.issueRst = issueRst;
    }

    public LocalDateTime getIssueAt() {
        return issueAt;
    }

    public void setIssueAt(LocalDateTime issueAt) {
        this.issueAt = issueAt;
    }

    public int getEnrollRst() {
        return enrollRst;
    }

    public void setEnrollRst(int enrollRst) {
        if (enrollRst != ENROLL_YES && enrollRst != ENROLL_NO)
            throw new RuntimeException("error param" + enrollRst);
        this.enrollRst = enrollRst;
    }

    public LocalDateTime getEnrollAt() {
        return enrollAt;
    }

    public void setEnrollAt(LocalDateTime enrollAt) {
        this.enrollAt = enrollAt;
    }

    public int getIssueBy() {
        return issueBy;
    }

    public void setIssueBy(int issueBy) {
        this.issueBy = issueBy;
    }

    public int getEnrollBy() {
        return enrollBy;
    }

    public void setEnrollBy(int enrollBy) {
        this.enrollBy = enrollBy;
    }

    public int getApplyBy() {
        return applyBy;
    }

    public void setApplyBy(int applyBy) {
        this.applyBy = applyBy;
    }

    public LocalDateTime getApplyAt() {
        return applyAt;
    }

    public void setApplyAt(LocalDateTime applyAt) {
        this.applyAt = applyAt;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getEnrollDate() {
        return enrollDate;
    }

    public void setEnrollDate(LocalDate enrollDate) {
        this.enrollDate = enrollDate;
    }

    public void clearIssueRst() {
        issueBy = 0;
        issueRst = 0;
    }

    public boolean withIssueRst() {
        return issueBy != 0 && (issueRst == ISSUE_YES || issueRst == ISSUE_NO);
    }

    public boolean isIssueYes() {
        return issueBy != 0 && issueRst == ISSUE_YES;
    }

    public boolean withEnrollRst() {
        return enrollBy != 0 && (enrollRst == ENROLL_YES || enrollRst == ENROLL_NO);
    }

    public boolean isEnrollYes() {
        return enrollBy != 0 && enrollRst == ENROLL_YES;
    }


}
