package com.szcgc.project.model;

import com.szcgc.comm.util.StringUtils;
import com.szcgc.project.constant.BusinessTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

/**
 * @Author liaohong
 * @create 2020/12/3 15:35
 */
@MappedSuperclass
public class ProjectSuperInfo {

    @Schema(description = "项目Id")
    @Column(updatable = false)
    protected int projectId;

    @Schema(description = "客户Id")
    @Column(updatable = false)
    protected int customerId;

    @Schema(description = "项目金额")
    //@Column(updatable = false)
    protected long amount;     //金额，单位:分

    @Schema(description = "业务品种")
    //@Column(updatable = false, length = 50)
    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    protected BusinessTypeEnum businessType;

    @Schema(description = "项目期限")
    //@Column(updatable = false)
    protected int during;

    @Schema(description = "项目期限单位")
    //@Column(updatable = false)
    protected int duringUnit;

    @Schema(description = "项目截止日")
    //@Column(updatable = false)
    protected LocalDate endDate;

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

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public BusinessTypeEnum getBusinessType() {
        return businessType;
    }

    public void setBusinessType(BusinessTypeEnum businessType) {
        this.businessType = businessType;
    }

    public void setBusinessType(String businessType) {
        if (!StringUtils.isEmpty(businessType)) {
            this.businessType = BusinessTypeEnum.valueOf(businessType);
        }
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

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void copy(ProjectSuperInfo info) {
        this.projectId = info.projectId;
        this.customerId = info.customerId;
        this.amount = info.amount;
        this.businessType = info.businessType;
        this.during = info.during;
        this.duringUnit = info.duringUnit;
        this.endDate = info.endDate;
    }
}
