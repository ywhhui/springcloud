package com.szcgc.project.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author liaohong
 * @create 2020/10/16 15:44
 * 放款审批(本质也是项目放款信息)
 */
@Entity
@Table(name = "loanapprovalinfo", schema = "gmis_project")
public class LoanApprovalInfo extends ProjectSuperInfo {

    //public static final long MAX_RISK_AMT = 300 * 10000 * 100;
    public static final int ISSUE_YES = 1;
    private static final int ISSUE_NO = 2;
    public static final int AUDIT_YES = 1;  //通过
    private static final int AUDIT_NO = 2;  //不通过
    public static final int AUDIT_YES_NOISSUE = 3; //通过且无需领导再次签发（保函用到）

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    @Schema(description = "担保意向Id")
//    @Column(updatable = false)
//    private int guaranteeIntentId;  //由此控制bankBranchId等字段

    @Schema(description = "签约Id")
    @Column(updatable = false)
    private int signId;  //由此控制bankBranchId等字段

    @Schema(description = "银行支行Id")
    @Column(updatable = false)
    private int bankBranchId;

    @Schema(description = "放款呈请人")
    private int applyBy;

    @Schema(description = "放款金额")
    private long applyAmt;

    @Schema(description = "呈请时间")
    private LocalDateTime applyAt;

    @Schema(description = "放款审核人")
    private int auditBy;

    @Schema(description = "审核意见(1:同意,2:不同意,3:同意无需再签发)")
    private int auditRst;

    @Schema(description = "审核时间")
    private LocalDateTime auditAt;

    @Schema(description = "放款签发人")
    private int issueBy;

    @Schema(description = "签发意见(1:同意,2:不同意)")
    private int issueRst;

    @Schema(description = "签发时间")
    private LocalDateTime issueAt;

    @Schema(description = "登记放款回执人")
    private int enrollBy;   //主流程信息

    @Schema(description = "登记放款回执时间")
    private LocalDateTime enrollAt; //主流程信息

    @Schema(description = "开始日期")
    private LocalDate beginDate; //主流程信息,登记放款回执时录入的

    @Schema(description = "结束日期")
    private LocalDate finishDate; //主流程信息,登记放款回执时录入的

    @Schema(description = "贷款利率")   //9%存到数据库的值是900  0.3%存到数据库的值是30
    private int loanRate; //主流程信息,登记放款回执时录入的

    @Schema(description = "放款回执文件")
    private int fileId; //主流程信息,登记放款回执时录入的

    @Schema(description = "放款通知书文件")
    private int loanNoticeFileId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public int getGuaranteeIntentId() {
//        return guaranteeIntentId;
//    }
//
//    public void setGuaranteeIntentId(int guaranteeIntentId) {
//        this.guaranteeIntentId = guaranteeIntentId;
//    }


    public int getSignId() {
        return signId;
    }

    public void setSignId(int signId) {
        this.signId = signId;
    }

    public int getBankBranchId() {
        return bankBranchId;
    }

    public void setBankBranchId(int bankBranchId) {
        this.bankBranchId = bankBranchId;
    }

    public int getApplyBy() {
        return applyBy;
    }

    public void setApplyBy(int applyBy) {
        this.applyBy = applyBy;
    }

    public long getApplyAmt() {
        return applyAmt;
    }

    public void setApplyAmt(long applyAmt) {
        this.applyAmt = applyAmt;
    }

    public LocalDateTime getApplyAt() {
        return applyAt;
    }

    public void setApplyAt(LocalDateTime applyAt) {
        this.applyAt = applyAt;
    }

    public int getAuditBy() {
        return auditBy;
    }

    public void setAuditBy(int auditBy) {
        this.auditBy = auditBy;
    }

    public int getAuditRst() {
        return auditRst;
    }

    public void setAuditRst(int auditRst) {
        if (auditRst != AUDIT_YES && auditRst != AUDIT_NO && auditRst != AUDIT_YES_NOISSUE)
            throw new RuntimeException("error param" + auditRst);
        this.auditRst = auditRst;
    }

    public LocalDateTime getAuditAt() {
        return auditAt;
    }

    public void setAuditAt(LocalDateTime auditAt) {
        this.auditAt = auditAt;
    }

    public int getIssueBy() {
        return issueBy;
    }

    public void setIssueBy(int issueBy) {
        this.issueBy = issueBy;
    }

    public int getIssueRst() {
        return issueRst;
    }

    public void setIssueRst(int issueRst) {
        if (auditRst != ISSUE_YES && auditRst != ISSUE_NO)
            throw new RuntimeException("error param" + auditRst);
        this.issueRst = issueRst;
    }

    public LocalDateTime getIssueAt() {
        return issueAt;
    }

    public void setIssueAt(LocalDateTime issueAt) {
        this.issueAt = issueAt;
    }

    public int getEnrollBy() {
        return enrollBy;
    }

    public void setEnrollBy(int enrollBy) {
        this.enrollBy = enrollBy;
    }

    public LocalDateTime getEnrollAt() {
        return enrollAt;
    }

    public void setEnrollAt(LocalDateTime enrollAt) {
        this.enrollAt = enrollAt;
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

    public int getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(int loanRate) {
        this.loanRate = loanRate;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public int getLoanNoticeFileId() {
        return loanNoticeFileId;
    }

    public void setLoanNoticeFileId(int loanNoticeFileId) {
        this.loanNoticeFileId = loanNoticeFileId;
    }

    public void clearAuditRst() {
        auditBy = 0;
        auditRst = 0;
    }

    public boolean withAuditRst() {
        return auditBy != 0 && (auditRst == AUDIT_YES || auditRst == AUDIT_NO || auditRst == AUDIT_YES_NOISSUE);
    }

    public boolean isAuditYes() {
        return auditBy != 0 && (auditRst == AUDIT_YES || auditRst == AUDIT_YES_NOISSUE);
    }

    public boolean isAuditYesWithoutIssue() {
        return auditBy != 0 && auditRst == AUDIT_YES_NOISSUE;
    }

    public boolean isIssueYes() {
        return issueBy != 0 && issueRst == ISSUE_YES;
    }

    public boolean withYesRst() {
        return isAuditYes() && isIssueYes();
    }

    public boolean isDoneEnroll() {
        return enrollBy != 0 && beginDate != null && finishDate != null;
    }

}
