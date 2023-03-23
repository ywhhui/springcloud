package com.szcgc.project.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Author liaohong
 * @create 2020/10/14 15:57
 * 项目B角意见&专审信息
 */
@Entity
@Table(name = "reviewinfo", schema = "gmis_project")
public class ReviewInfo {

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "项目Id")
    @Column(updatable = false)
    private int projectId;

    @Schema(description = "B角意见")
    @Column(updatable = false, length = 200)
    private String comment;

    @Schema(description = "B角Id/填写人")
    @Column(updatable = false)
    private int accountId;

    @Schema(description = "专审结论")
    @Column(length = 50)
    private String evaluateFlow;

    @Schema(description = "专审结论描述")
    @Column(length = 200)
    private String conclusion;

    @Schema(description = "暂缓理由")
    @Column(length = 999)
    private String stopReason;

    @Column(updatable = false)
    private LocalDateTime createAt;

    private LocalDateTime updateAt;

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getEvaluateFlow() {
        return evaluateFlow;
    }

    public void setEvaluateFlow(String evaluateFlow) {
        this.evaluateFlow = evaluateFlow;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
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

    public String getStopReason() {
        return stopReason;
    }

    public void setStopReason(String stopReason) {
        this.stopReason = stopReason;
    }

}
