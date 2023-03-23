package com.szcgc.project.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Author liaohong
 * @create 2020/9/15 18:30
 * 修改评审意见表
 */
@Entity
@Table(name = "conclusioninfo", schema = "gmis_project")
public class ConclusionInfo {

    //public static final int CATE_REASON = 1;

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "项目Id")
    @Column(updatable = false)
    private int projectId;

    @Schema(description = "账号Id")
    @Column(updatable = false)
    private int accountId;

    @Schema(description = "修改原因")
    @Column(length = 200)
    private String reason;

    @Schema(description = "基本结论")
    @Column(length = 2000)
    private String evaluate;

    @Schema(description = "收费")
    @Column(length = 5000)
    private String fee;

    @Schema(description = "反担保措施")
    @Column(length = 2000)
    private String counterGuarantee;

    @Schema(description = "评委意见")
    @Column(length = 2000)
    private String meetOpinion;

    @Schema(description = "分担比例")
    @Column(length = 2000)
    private String supervisor;

    @Schema(description = "创建时间")
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getCounterGuarantee() {
        return counterGuarantee;
    }

    public void setCounterGuarantee(String counterGuarantee) {
        this.counterGuarantee = counterGuarantee;
    }

    public String getMeetOpinion() {
        return meetOpinion;
    }

    public void setMeetOpinion(String meetOpinion) {
        this.meetOpinion = meetOpinion;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    @PrePersist
    public void onCreate() {
        createAt = LocalDateTime.now();
    }
}
