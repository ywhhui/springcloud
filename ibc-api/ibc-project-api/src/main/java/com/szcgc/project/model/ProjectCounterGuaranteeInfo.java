package com.szcgc.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @Author liaohong
 * @create 2020/9/24 10:21
 * 项目反担保措施表
 */
@Entity
@Table(name = "pcounterguaranteeinfo", schema = "gmis_project")
public class ProjectCounterGuaranteeInfo {

    public static final int GRT_UNABLE = -2;  //这个字段仅满足业务需要，数据库不会出现该字段
    public static final int GRT_IDLE = -1;  //空闲状态(数据库不会出现该字段)
    public static final int GRT_INIT = 0;   //已提交(初始化)
    public static final int GRT_SEL = 1;    //已选用
    public static final int GRT_CHK = 2;    //已办理
    public static final int GRT_REL = 3;    //已释放

    private static final int MATERIAL_Y = 1;
    private static final int MATERIAL_N = 0;

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "项目Id")
    @Column(updatable = false)
    private int projectId;

    @Schema(description = "客户Id")
    @Column(updatable = false)
    private int customerId;

    @Schema(description = "反担保措施Id")
    @Column(updatable = false)
    private int counterGuaranteeId;

    @Schema(description = "方案Id")
    @Column(updatable = false)
    private int proposalId;

    @Schema(description = "是否保证物")
    @Column(updatable = false)
    private int material; //冗余

    @Schema(description = "选用状态(0:初始化,1:已选用,2:已办理,3:已释放)")
    private int guaranteeing;   //已选即是保证中状态

//    @Schema(description = "评估状态(0:无需评估,1:需要评估,2:已分配,3:已评估)")
//    private int assessing;

    @Schema(description = "选用人")
    private int selectBy;

    @Schema(description = "选用时间")
    private LocalDateTime selectAt;


    @Schema(description = "登记证")
    private int fileId;

    @Schema(description = "登记人")
    private int dealBy;

    @Schema(description = "登记时间")
    private LocalDateTime dealAt;


    @Schema(description = "办理人")
    private int checkBy;

    @Schema(description = "办理时间")
    private LocalDateTime checkAt;

    @Schema(description = "备注")
    @Column(length = 50)
    private String remarks;

    @Schema(description = "创建人")
    @Column(updatable = false)
    private int createBy;

    @Schema(description = "创建时间")
    @Column(updatable = false)
    private LocalDateTime createAt;

    @Schema(description = "更新时间")
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

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCounterGuaranteeId() {
        return counterGuaranteeId;
    }

    public void setCounterGuaranteeId(int counterGuaranteeId) {
        this.counterGuaranteeId = counterGuaranteeId;
    }

    public int getProposalId() {
        return proposalId;
    }

    public void setProposalId(int proposalId) {
        this.proposalId = proposalId;
    }

    public int getGuaranteeing() {
        return guaranteeing;
    }

    public void setGuaranteeing(int using) {
        this.guaranteeing = using;
    }

//    public int getAssessing() {
//        return assessing;
//    }
//
//    public void setAssessing(int assessing) {
//        this.assessing = assessing;
//    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public int getDealBy() {
        return dealBy;
    }

    public void setDealBy(int dealBy) {
        this.dealBy = dealBy;
    }

    public LocalDateTime getDealAt() {
        return dealAt;
    }

    public void setDealAt(LocalDateTime dealAt) {
        this.dealAt = dealAt;
    }

    public int getSelectBy() {
        return selectBy;
    }

    public void setSelectBy(int selectBy) {
        this.selectBy = selectBy;
    }

    public LocalDateTime getSelectAt() {
        return selectAt;
    }

    public void setSelectAt(LocalDateTime selectAt) {
        this.selectAt = selectAt;
    }

    public int getCheckBy() {
        return checkBy;
    }

    public void setCheckBy(int checkBy) {
        this.checkBy = checkBy;
    }

    public LocalDateTime getCheckAt() {
        return checkAt;
    }

    public void setCheckAt(LocalDateTime checkAt) {
        this.checkAt = checkAt;
    }

    public int getMaterial() {
        return material;
    }

    public void setMaterial(int material) {
        this.material = material;
    }

    @JsonIgnore
    public void setMaterial(boolean material) {
        this.material = material ? MATERIAL_Y : MATERIAL_N;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getCreateBy() {
        return createBy;
    }

    public void setCreateBy(int createBy) {
        this.createBy = createBy;
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

    public boolean isNormal() {
        return guaranteeing == GRT_SEL || guaranteeing == GRT_CHK;
    }

    public boolean isMaterial() {
        return material == MATERIAL_Y;
    }

    public boolean isNotBak() {
        if (counterGuaranteeId <= 0 || customerId <= 0 || projectId <= 0)
            return false;
        return true;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectCounterGuaranteeInfo that = (ProjectCounterGuaranteeInfo) o;
        return id == that.id &&
                projectId == that.projectId &&
                customerId == that.customerId &&
                counterGuaranteeId == that.counterGuaranteeId &&
                guaranteeing == that.guaranteeing;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, projectId, customerId, counterGuaranteeId, guaranteeing);
    }
}
