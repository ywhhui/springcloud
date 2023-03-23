package com.szcgc.bpmn.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Author liaohong
 * @create 2020/9/25 17:14
 * 项目日志
 */
@Entity
@Table(name = "actioninfo", schema = "gmis_process")
public class ProjectActionInfo {

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "项目Id")
    @Column(updatable = false)
    private int projectId;

    @Schema(description = "项目Id")
    @Column(updatable = false)
    private int accountId;

    @Schema(description = "动作")
    @Column(length = 20, updatable = false)
    private String caseName;

    @Schema(description = "动作名")
    @Column(length = 20, updatable = false)
    private String caseNameCn;

    @Schema(description = "任务Id")
    @Column(length = 50, updatable = false)
    private String taskId;

    @Schema(description = "任务生成时间")
    //@Column(updatable = false)
    private LocalDateTime taskCreateAt;

    @Schema(description = "关联Id")
    @Column(updatable = false)
    private int refId;

    @Schema(description = "动作选项")
    @Column(length = 50, updatable = false)
    private String opinion;

    @Schema(description = "意见(前端可见)")
    @Column(length = 200, updatable = false)
    private String remarks;

    @Schema(description = "备注(前端不可见)")
    @Column(length = 200, updatable = false)
    private String extra;

    @Schema(description = "创建时间")
    @Column(updatable = false)
    private LocalDateTime createAt;

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

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getCaseNameCn() {
        return caseNameCn;
    }

    public void setCaseNameCn(String caseNameCn) {
        this.caseNameCn = caseNameCn;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public LocalDateTime getTaskCreateAt() {
        return taskCreateAt;
    }

    public void setTaskCreateAt(LocalDateTime taskCreateAt) {
        this.taskCreateAt = taskCreateAt;
    }

    public int getRefId() {
        return refId;
    }

    public void setRefId(int refId) {
        this.refId = refId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String filterRemarks() {
        if (remarks != null && remarks.charAt(0) == '[')
            return "";
        return remarks;
    }

}
