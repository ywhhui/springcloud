package com.szcgc.project.model;

import com.szcgc.project.constant.BusinessTypeCateEnum;
import com.szcgc.project.constant.ProjectConstants;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Author liaohong
 * @create 2020/12/11 17:14
 * 项目额度
 */
@Entity
@Table(name = "creditinfo", schema = "gmis_project")
public class CreditInfo {

    @Id
    @Column(name = "id", length = 11)
    private int id;

    @Schema(description = "客户Id")
    @Column(updatable = false)
    private int customerId;

    @Schema(description = "账号Id")
    @Column(updatable = false)
    private int accountId;

    @Schema(description = "项目编码")
    @Column(length = 50, updatable = false)
    private String projectCode;

    @Column(updatable = false)
    @Schema(description = "额度品种")
    @Enumerated(EnumType.STRING)
    private BusinessTypeCateEnum cate;

    @Schema(description = "签约Id")
    @Column(updatable = false)
    private int signId;

    @Schema(description = "总额度")
    @Column(updatable = false)
    private long amount;

    @Schema(description = "期限")
    @Column(updatable = false)
    private int during;

    @Schema(description = "申请期限单位")
    @Column(updatable = false)
    private int duringUnit;

    @Schema(description = "备注")
    @Column(length = 200, updatable = false)
    private String remarks;

    @Schema(description = "是否可突破限额")
    @Column(updatable = false)
    private int breakable;  //默认0表示不可突破 取值1表示可突破

    @Schema(description = "生成时间")
    @Column(updatable = false)
    private LocalDateTime createAt;

    @Schema(description = "启用时间")
    private LocalDateTime beginAt;

    @Schema(description = "结束时间")
    private LocalDateTime endAt;

    @Schema(description = "剩余额度")
    private long amountLeft;

    @Schema(description = "更新时间")
    private LocalDateTime updateAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public BusinessTypeCateEnum getCate() {
        return cate;
    }

    public void setCate(BusinessTypeCateEnum cate) {
        this.cate = cate;
    }

    public int getSignId() {
        return signId;
    }

    public void setSignId(int signId) {
        this.signId = signId;
    }

    public long getAmountLeft() {
        return amountLeft;
    }

    public void setAmountLeft(long amountLeft) {
        this.amountLeft = amountLeft;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getBreakable() {
        return breakable;
    }

    public void setBreakable(int breakable) {
        this.breakable = breakable;
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

    public int getDuring() {
        return during;
    }

    public void setDuring(int during) {
        this.during = during;
    }

    public int getDuringUnit() {
        return duringUnit;
    }

    public void setDuringUnit(int duringUnit) {
        this.duringUnit = duringUnit;
    }

    public LocalDateTime getBeginAt() {
        return beginAt;
    }

    public void setBeginAt(LocalDateTime beginAt) {
        this.beginAt = beginAt;
        if (duringUnit == ProjectConstants.DURING_UNIT_MONTH) {
            setEndAt(beginAt.plusMonths(during));
        } else if (duringUnit == ProjectConstants.DURING_UNIT_DAY) {
            setEndAt(beginAt.plusDays(during));
        } else {
            setEndAt(beginAt);
        }
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public void setEndAt(LocalDateTime endAt) {
        this.endAt = endAt;
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

    public boolean isExpired() {
        if (endAt == null)
            return false;
        return endAt.isBefore(LocalDateTime.now());
    }

}
