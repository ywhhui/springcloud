package com.szcgc.project.model;

import com.szcgc.project.constant.BusinessTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author liaohong
 * @create 2020/10/21 11:21
 * 还款证明书
 */
@Entity
@Table(name = "repaycertinfo", schema = "gmis_project")
public class RepayCertificateInfo {

    public static final int AUDIT_YES = 1;
    private static final int AUDIT_NO = 2;

    private static final int CONFIRM_YES = 1;
    private static final int CONFIRM_NO = 2;

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "项目Id")
    @Column(updatable = false)
    private int projectId;

    @Schema(description = "客户Id")
    @Column(updatable = false)
    protected int customerId;

    @Schema(description = "业务品种")
    @Column(updatable = false, length = 50)
    @Enumerated(EnumType.STRING)
    protected BusinessTypeEnum businessType;

    @Schema(description = "还款日期")
    @Column(updatable = false)
    private LocalDate repayAt;

    @Schema(description = "撤保金额")
    @Column(updatable = false)
    private long amount;

    @Schema(description = "还款证明书")
    @Column(updatable = false)
    private int fileId;

    @Schema(description = "终止说明")
    @Column(length = 200, updatable = false)
    private String remarks;

    @Schema(description = "录入用户Id")
    @Column(updatable = false)
    private int accountId;

    @Schema(description = "创建时间")
    @Column(updatable = false)
    private LocalDateTime createAt;

    @Schema(description = "审核用户Id")
    private int auditBy;

    @Schema(description = "审核时间")
    private LocalDateTime auditAt;

    @Schema(description = "审核意见(1:同意,2:不同意)")
    private int auditRst;

    @Schema(description = "确认领导Id(保函项目)")
    private int confirmBy;

    @Schema(description = "确认时间(保函项目)")
    private LocalDateTime confirmAt;

    @Schema(description = "领导确认意见(保函项目,1:同意,2:不同意)")
    private int confirmRst;

    @Schema(description = "领导意见(保函项目)")
    @Column(length = 500)
    private String confirmOpinion;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public BusinessTypeEnum getBusinessType() {
        return businessType;
    }

    public void setBusinessType(BusinessTypeEnum businessType) {
        this.businessType = businessType;
    }

    public LocalDate getRepayAt() {
        return repayAt;
    }

    public void setRepayAt(LocalDate repayAt) {
        this.repayAt = repayAt;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public int getAuditBy() {
        return auditBy;
    }

    public void setAuditBy(int auditBy) {
        this.auditBy = auditBy;
    }

    public LocalDateTime getAuditAt() {
        return auditAt;
    }

    public void setAuditAt(LocalDateTime auditAt) {
        this.auditAt = auditAt;
    }

    public int getAuditRst() {
        return auditRst;
    }

    public void setAuditRst(int auditRst) {
        if (auditRst != AUDIT_YES && auditRst != AUDIT_NO)
            throw new RuntimeException("error param" + auditRst);
        this.auditRst = auditRst;
    }

    public int getConfirmBy() {
        return confirmBy;
    }

    public void setConfirmBy(int confirmBy) {
        this.confirmBy = confirmBy;
    }

    public LocalDateTime getConfirmAt() {
        return confirmAt;
    }

    public void setConfirmAt(LocalDateTime confirmAt) {
        this.confirmAt = confirmAt;
    }

    public int getConfirmRst() {
        return confirmRst;
    }

    public void setConfirmRst(int confirmRst) {
        if (confirmRst != CONFIRM_YES && confirmRst != CONFIRM_NO)
            throw new RuntimeException("error param" + confirmRst);
        this.confirmRst = confirmRst;
    }

    public String getConfirmOpinion() {
        return confirmOpinion;
    }

    public void setConfirmOpinion(String confirmOpinion) {
        this.confirmOpinion = confirmOpinion;
    }

    public boolean isAuditYes() {
        return auditBy != 0 && auditRst == AUDIT_YES;
    }

    public boolean iConfirmYes() {
        return confirmBy != 0 && confirmRst == CONFIRM_YES;
    }

    public boolean withAuditRst() {
        return auditBy != 0 && (auditRst == AUDIT_YES || auditRst == AUDIT_NO);
    }

    public boolean withConfirmRst() {
        return confirmBy != 0 && (confirmRst == CONFIRM_YES || confirmRst == CONFIRM_NO);
    }
}
