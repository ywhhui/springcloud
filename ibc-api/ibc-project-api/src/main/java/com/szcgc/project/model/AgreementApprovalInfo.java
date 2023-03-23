package com.szcgc.project.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Author liaohong
 * @create 2020/11/20 10:53
 * 合同制作-流程审核
 */
@Entity
@Table(name = "agreementapprovalinfo", schema = "gmis_project")
public class AgreementApprovalInfo {

    public static final int AUDIT_YES = 1;
    public static final int AUDIT_NO = 2;
    public static final int MAKE_NML = 11;
    public static final int MAKE_UNML = 12;

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "项目Id")
    @Column(updatable = false)
    private int projectId;

    @Schema(description = "处理人")
    private int accountId;

    @Schema(description = "审批节点")
    @Column(length = 50)
    private String auditName;

    @Schema(description = "处理结果(1:同意,2:不同意,11:制作标准合同,12:制作非标合同)")
    private int auditRst;

    @Schema(description = "处理时间")
    private LocalDateTime auditAt;

    @Schema(description = "备注")
    @Column(length = 100)
    private String remarks;

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

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAuditName() {
        return auditName;
    }

    public void setAuditName(String auditName) {
        this.auditName = auditName;
    }

    public int getAuditRst() {
        return auditRst;
    }

    public void setAuditRst(int auditRst) {
        this.auditRst = auditRst;
    }

    public LocalDateTime getAuditAt() {
        return auditAt;
    }

    public void setAuditAt(LocalDateTime auditAt) {
        this.auditAt = auditAt;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean isMakeAct() {
        return auditRst == MAKE_NML || auditRst == MAKE_UNML;
    }

    public boolean isMakeNml() {
        return auditRst == MAKE_NML;
    }

    public boolean isAuditYes() {
        return auditRst == AUDIT_YES;
    }
}
