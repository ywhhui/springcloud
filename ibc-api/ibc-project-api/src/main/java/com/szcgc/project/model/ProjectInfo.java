package com.szcgc.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.szcgc.comm.util.StringUtils;
import com.szcgc.project.constant.BusinessTypeEnum;
import com.szcgc.project.constant.EnterprisesTypeEnum;
import com.szcgc.project.constant.ProjectStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.text.Format;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author liaohong
 * @create 2020/9/15 16:26
 * 项目主表
 */
@Data
@Entity
@Table(name = "projectinfo", schema = "gmis_project")
public class ProjectInfo {

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "项目编码")
    @Column(length = 50, updatable = false)
    private String code;

    @Schema(description = "客户名称")
    @Column(length = 50, updatable = false)
    private String name;

    @Schema(description = "客户Id")
    @Column(updatable = false)
    private int customerId; //客户Id

    @Schema(description = "申请金额")
    @Column(updatable = false)
    private long amount;     //金额，单位:分

    @Column(updatable = false)
    @Schema(description = "申请业务品种")
    private String businessType;

    @Schema(description = "申请期限")
    @Column(updatable = false)
    private int during;    //申请期限

    @Schema(description = "申请期限单位")
    @Column(updatable = false)
    private int duringUnit;    //申请期限单位

    @Schema(description = "申请截止日")
    @Column(updatable = false)
    private LocalDate endDate;

    @Schema(description = "项目状态")
    @Column(updatable = false)
    @Enumerated(EnumType.STRING)
    private ProjectStatusEnum ibcStatus;      //项目状态

    @Column(updatable = false)
    private int createBy;

    @Column(updatable = false)
    private LocalDateTime createAt;

    @Schema(description = "手动操作")
    @Column(updatable = false, length = 50)
    @JsonIgnore
    private String currManual;

    @Schema(description = "关联项目")
    @JsonIgnore
    @Column(updatable = false)
    private int refProjectId;

    @Schema(description = "企业类型")
    @Column(updatable = false)
    @Enumerated(EnumType.STRING)
    private EnterprisesTypeEnum enterprisesType;

    @Schema(description = "合同年份")
    @Column(updatable = false)
    private int contYear;

    @Schema(description = "合同字号")
    @Column(updatable = false, length = 50)
    private String contNo;

    private int updateBy;

    private LocalDateTime updateAt;

    @JsonIgnore
    public BusinessTypeEnum getBusinessTypeEnum() {
        if (businessType == null)
            return null;
        return BusinessTypeEnum.valueOf(businessType);
    }

    @JsonIgnore
    public String getBusinessTypeName() {
        if (businessType == null)
            return null;
        BusinessTypeEnum bt = BusinessTypeEnum.valueOf(businessType);
        return bt.getCate().getCnName() + "-" + bt.getCnName();
    }

    @JsonIgnore
    public String getContNoFormat() {
        if (StringUtils.isEmpty(contNo))
            return "";
        Format f = new DecimalFormat("0000");
        int no = getContNoDigital();
        return contNo.contains("N") ? "N" + f.format(no) : f.format(no);
    }

    @JsonIgnore
    public int getContNoDigital() {
        return StringUtils.isEmpty(contNo) ? 0 : Integer.parseInt(contNo.replace("N", ""));
    }

    @JsonIgnore
    public boolean isCurrManualIng() {
        return currManual != null && currManual.length() > 1;
    }

}
