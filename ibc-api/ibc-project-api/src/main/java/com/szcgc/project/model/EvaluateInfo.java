package com.szcgc.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.szcgc.comm.util.StringUtils;
import com.szcgc.project.constant.InterestTypeEnum;
import com.szcgc.project.constant.LendTypeEnum;
import com.szcgc.project.constant.ProjectConstants;
import com.szcgc.project.constant.RepayTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author liaohong
 * @create 2020/9/15 18:09
 * 项目评审意见表
 */
@Entity
@Table(name = "evaluateinfo", schema = "gmis_project")
public class EvaluateInfo extends ProjectSuperInfo {

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "会议Id")
    @Column(updatable = false)
    private int meetingId;

    @Schema(description = "会议日期")
    @Column(updatable = false)
    private LocalDate meetingDate; //通过meetingId也可以拿到，冗余

    @Schema(description = "方案Id")
    @Column(updatable = false)
    private int proposalId; //暂未启用

    @Schema(description = "专审Id")
    @Column(updatable = false)
    private int reviewId;//暂未启用

    @Schema(description = "评审次数")
    @Column(updatable = false)
    private int times;

    @Schema(description = "任务Id")
    @Column(updatable = false, length = 50)
    private String taskId;

    @Schema(description = "评审结论")
    @Column(length = 50)
    private String evaluateFlow;

    @Schema(description = "操作主体")
    private int operateObject;

    @Schema(description = "银行支行Id")
    private int bankBranchId;

    @Schema(description = "还款方式")
    @Enumerated(EnumType.STRING)
    private RepayTypeEnum repayType;

    @Schema(description = "定期还款金额")
    private long repayAmt;

    @Schema(description = "其他付息方式")
    @Column(length = 500)
    private String interestOthers;

    @Schema(description = "资金成本")
    @Column(columnDefinition = "DECIMAL(6,3)")
    private double capitalCost;

    @Schema(description = "项目利率")
    @Column(columnDefinition = "DECIMAL(6,3)")
    private double projectRate;

//    @Schema(description = "还放款备注")
//    @Column(length = 200)
//    private String repayRemarks;

    @Schema(description = "放款方式")
    @Enumerated(EnumType.STRING)
    private LendTypeEnum lendType;

    @Schema(description = "其他放款方式")
    @Column(length = 500)
    private String lendOthers;

    @Schema(description = "付息方式")
    @Enumerated(EnumType.STRING)
    private InterestTypeEnum interestType;

    @Schema(description = "其他还款方式")
    @Column(length = 500)
    private String repayOthers;

    @Schema(description = "评审结论描述")
    @Column(length = 500)
    private String conclusion;

    @Schema(description = "评审结论标签")
    @Column(length = 200)
    private String tags;

    @Schema(description = "处理人Id")
    @Column(updatable = false)
    private int accountId;

    @Column(updatable = false)
    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public int getProposalId() {
        return proposalId;
    }

    public void setProposalId(int proposalId) {
        this.proposalId = proposalId;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public LocalDate getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(LocalDate meetDate) {
        this.meetingDate = meetDate;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getEvaluateFlow() {
        return evaluateFlow;
    }

    public void setEvaluateFlow(String evaluateFlow) {
        this.evaluateFlow = evaluateFlow;
    }

    public int getBankBranchId() {
        return bankBranchId;
    }

    public void setBankBranchId(int bankBranchId) {
        this.bankBranchId = bankBranchId;
    }

    public RepayTypeEnum getRepayType() {
        return repayType;
    }

    public void setRepayType(RepayTypeEnum repayType) {
        this.repayType = repayType;
    }

    public String getInterestOthers() {
        return interestOthers;
    }

    public long getRepayAmt() {
        return repayAmt;
    }

    public void setRepayAmt(long repayAmt) {
        this.repayAmt = repayAmt;
    }

//    public String getRepayRemarks() {
//        return repayRemarks;
//    }
//
//    public void setRepayRemarks(String repayRemarks) {
//        this.repayRemarks = repayRemarks;
//    }

    public LendTypeEnum getLendType() {
        return lendType;
    }

    public void setLendType(LendTypeEnum lendType) {
        this.lendType = lendType;
    }

    public void setInterestOthers(String interestOthers) {
        this.interestOthers = interestOthers;
    }

//    public String getLendRemarks() {
//        return lendRemarks;
//    }
//
//    public void setLendRemarks(String lendRemarks) {
//        this.lendRemarks = lendRemarks;
//    }

    public InterestTypeEnum getInterestType() {
        return interestType;
    }

    public void setInterestType(InterestTypeEnum interestType) {
        this.interestType = interestType;
    }

    public String getLendOthers() {
        return lendOthers;
    }

    public double getCapitalCost() {
        return capitalCost;
    }

    public void setCapitalCost(double capitalCost) {
        this.capitalCost = capitalCost;
    }

    public double getProjectRate() {
        return projectRate;
    }

    public void setProjectRate(double projectRate) {
        this.projectRate = projectRate;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public int getOperateObject() {
        return operateObject;
    }

    public void setOperateObject(int operateObject) {
        this.operateObject = operateObject;
    }

    public String getTags() {
        return tags;
    }

    public void setLendOthers(String lendOthers) {
        this.lendOthers = lendOthers;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    @PrePersist
    public void onCreate() {
        createAt = LocalDateTime.now();
        updateAt = createAt;
    }

    @PreUpdate
    public void onUpdate() {
        updateAt = LocalDateTime.now();
    }

    @JsonIgnore
    public boolean isFlowHold() {
        return ProjectConstants.EVALUATE_FLOW_HOLD.equals(evaluateFlow);
    }

    @JsonIgnore
    public boolean isFlowStop() {
        return ProjectConstants.EVALUATE_FLOW_STOP.equals(evaluateFlow);
    }

    @JsonIgnore
    public boolean isFlowPass() {
        return ProjectConstants.EVALUATE_FLOW_PASS.equals(evaluateFlow);
    }

    @JsonIgnore
    public boolean isFlowAddendum() {
        return ProjectConstants.EVALUATE_FLOW_ADDENDUM.equals(evaluateFlow);
    }

    public String getRepayOthers() {
        return repayOthers;
    }

    public void setRepayOthers(String repayOthers) {
        this.repayOthers = repayOthers;
    }

    public void setInterestType(String interestType) {
        if (StringUtils.isEmpty(interestType)) {
            return;
        }
        this.interestType = InterestTypeEnum.valueOf(interestType);
    }

    public void setLendType(String lendType) {
        if (StringUtils.isEmpty(lendType)) {
            return;
        }
        this.lendType = LendTypeEnum.valueOf(lendType);
    }

    public void setRepayType(String repayType) {
        if (StringUtils.isEmpty(repayType)) {
            return;
        }
        this.repayType = RepayTypeEnum.valueOf(repayType);
    }

    public void setTags(String tags) {
        if (tags == null) {
            return;
        }
        if (tags.length() > 200) {
            tags = StringUtils.ending(tags, 200);
        }
        this.tags = tags;
    }

}
