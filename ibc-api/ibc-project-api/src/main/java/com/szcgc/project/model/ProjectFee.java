package com.szcgc.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.szcgc.comm.util.StringUtils;
import com.szcgc.project.constant.FeeTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author: chenxinli
 * @Date: 2020/8/24 19:18
 * @Description:
 */

@Entity
@Table(schema = "gmis_project", name = "project_fee")
@Schema(description = "项目收费表，记录评审会记录的收费标准、财务的确认的收费标准和财务实际确认的收费")
public class ProjectFee {

    public static final int REFUND_N=0;
    public static final int REFUND_Y=1;

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @Schema(description = "项目id")
    private int projectId;

    @Column
    @Schema(description = "评审会结论id")
    private int projectEvaluateInfoId;

    @Column
    @Schema(description = "收费大类")
    @Enumerated(EnumType.STRING)
    private FeeTypeEnum feeTypeEnum;

    @Schema(description = "费率")
    private int rate;

    @Column
    @Schema(description = "评审会记录的金额")
    private long recordAmount;

    @Column
    @Schema(description = "评审会记录人员")
    private int recordPerson;

    @Column
    @Schema(description = "评审会记录收费时间")
    @JsonIgnore
    private LocalDateTime recordDatetime;

    @Column
    @Schema(description = "计划收费时间，用于启动定时任务")
    private LocalDate scheduleDate;

    @Column
    @Schema(description = "确认收费标准金额")
    private long confirmAmount;

    @Column
    @Schema(description = "确认收费标准人员")
    private int confirmPerson;

    @Column
    @Schema(description = "确认收费标准时间")
    @JsonIgnore
    private LocalDateTime confirmDatetime;

    @Column
    @Schema(description = "实际收费金额")
    private long receivedAmount;

    @Column
    @Schema(description = "实际收费人员")
    private int receivedPerson;

    @Column
    @Schema(description = "实际收费时间")
    private LocalDate receivedDateActual;

    @Column
    @Schema(description = "录入实际收费时间")
    @JsonIgnore
    private LocalDateTime receivedDatetime;

    @Column
    @Schema(description = "是否退款，0未退款，1已退款")
    private int refund;

    @Column
    @Schema(description = "退款金额")
    private long refundAmount;

    @Column
    @Schema(description = "录入实际退款时间")
    @JsonIgnore
    private LocalDateTime refundDatetime;

    @Column
    @Schema(description = "实际退费时间")
    private LocalDate refundDateActual;

    @Column
    @Schema(description = "实际退款人员")
    private int refundPerson;

//    @Column
//    @Schema(description = "收款方式")
//    @Enumerated(EnumType.STRING)
//    private FeeCollectionTypeEnum feeCollectionType;

    @Column(length = 30)
    @Schema(description = "该笔费用从哪个项目转移过来的")
    private int soucreProjectId;

    @Column
    @Schema(description = "备注")
    private String remark;

    @Column
    @Schema(description = "退转保证金备注")
    public String refundRemark;

    @JsonIgnore
    private int createBy;

    @JsonIgnore
    private LocalDateTime createAt;

    @JsonIgnore
    private int updateBy;

    @JsonIgnore
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

    public int getProjectEvaluateInfoId() {
        return projectEvaluateInfoId;
    }

    public void setProjectEvaluateInfoId(int projectEvaluateInfoId) {
        this.projectEvaluateInfoId = projectEvaluateInfoId;
    }

    public FeeTypeEnum getFeeTypeEnum() {
        return feeTypeEnum;
    }

    public void setFeeTypeEnum(FeeTypeEnum itemType) {
        this.feeTypeEnum = itemType;
    }

    public void setItemType(String itemType) {
        if (StringUtils.isEmpty(itemType))
            return;
        this.feeTypeEnum = FeeTypeEnum.valueOf(itemType);
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public long getRecordAmount() {
        return recordAmount;
    }

    public void setRecordAmount(long recordAmount) {
        this.recordAmount = recordAmount;
    }

    public int getRecordPerson() {
        return recordPerson;
    }

    public void setRecordPerson(int recordPerson) {
        this.recordPerson = recordPerson;
    }

    public LocalDateTime getRecordDatetime() {
        return recordDatetime;
    }

    public void setRecordDatetime(LocalDateTime recordDatetime) {
        this.recordDatetime = recordDatetime;
    }

    public LocalDate getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(LocalDate scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public long getConfirmAmount() {
        return confirmAmount;
    }

    public void setConfirmAmount(long confirmAmount) {
        this.confirmAmount = confirmAmount;
    }

    public int getConfirmPerson() {
        return confirmPerson;
    }

    public void setConfirmPerson(int confirmPerson) {
        this.confirmPerson = confirmPerson;
    }

    public LocalDateTime getConfirmDatetime() {
        return confirmDatetime;
    }

    public void setConfirmDatetime(LocalDateTime confirmDatetime) {
        this.confirmDatetime = confirmDatetime;
    }

    public long getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(long receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public int getReceivedPerson() {
        return receivedPerson;
    }

    public void setReceivedPerson(int receivedPerson) {
        this.receivedPerson = receivedPerson;
    }

    public LocalDate getReceivedDateActual() {
        return receivedDateActual;
    }

    public void setReceivedDateActual(LocalDate receivedDateActual) {
        this.receivedDateActual = receivedDateActual;
    }

    public LocalDateTime getReceivedDatetime() {
        return receivedDatetime;
    }

    public void setReceivedDatetime(LocalDateTime receivedDatetime) {
        this.receivedDatetime = receivedDatetime;
    }

    @JsonIgnore
    public boolean isReceived(){
        return this.receivedAmount>0 && this.receivedPerson>0 && this.receivedDateActual!=null;
    }

    public int getRefund() {
        return refund;
    }

    public void setRefund(int refund) {
        this.refund = refund;
    }

    public long getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(long refundAmount) {
        this.refundAmount = refundAmount;
    }

    public LocalDateTime getRefundDatetime() {
        return refundDatetime;
    }

    public void setRefundDatetime(LocalDateTime refundDatetime) {
        this.refundDatetime = refundDatetime;
    }

    public int getRefundPerson() {
        return refundPerson;
    }

    public void setRefundPerson(int refundPerson) {
        this.refundPerson = refundPerson;
    }

//    public FeeCollectionTypeEnum getFeeCollectionType() {
//        return feeCollectionType;
//    }
//
//    public void setFeeCollectionType(FeeCollectionTypeEnum feeCollectionType) {
//        this.feeCollectionType = feeCollectionType;
//    }
//
//    public void setFeeCollectionType(String feeCollectionType) {
//        if (StringUtils.isEmpty(feeCollectionType))
//            return;
//        this.feeCollectionType = FeeCollectionTypeEnum.valueOf(feeCollectionType);
//    }

    public int getSoucreProjectId() {
        return soucreProjectId;
    }

    public void setSoucreProjectId(int soucreProjectId) {
        this.soucreProjectId = soucreProjectId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDate getRefundDateActual() {
        return refundDateActual;
    }

    public void setRefundDateActual(LocalDate refundDateActual) {
        this.refundDateActual = refundDateActual;
    }

    public String getRefundRemark() {
        return refundRemark;
    }

    public void setRefundRemark(String refundRemark) {
        this.refundRemark = refundRemark;
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

    public int getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(int updateBy) {
        this.updateBy = updateBy;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    @PrePersist
    public void onCreate(){
        createAt = LocalDateTime.now();
        updateAt= createAt;
    }

    @PreUpdate
    public void onUpdate(){
        updateAt = LocalDateTime.now();
    }
}
