package com.szcgc.project.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Author: liaohong
 * @Date: 2020/8/7 10:14
 * @Description:
 */
@Entity
@Table(name = "creditrcdinfo", schema = "gmis_project")
public class CreditRecordInfo {

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(updatable = false)
    @Schema(description = "额度id")
    private int creditId;

    @Column(updatable = false)
    @Schema(description = "客户Id")
    private int customerId; //冗余

    @Column(updatable = false)
    @Schema(description = "项目Id")
    private int projectId;  //影响creditId变化的项目

    @Column(updatable = false)
    @Schema(description = "额度变化值")
    private long amount;

    @Schema(description = "备注")
    @Column(length = 200)
    private String remarks;

    @Schema(description = "生成时间")
    @Column(updatable = false)
    private LocalDateTime createAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCreditId() {
        return creditId;
    }

    public void setCreditId(int creditId) {
        this.creditId = creditId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
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

    @PrePersist
    public void onCreate() {
        createAt = LocalDateTime.now();
    }
}
