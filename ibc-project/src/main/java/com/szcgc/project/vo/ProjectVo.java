package com.szcgc.project.vo;

import com.szcgc.project.model.ProjectInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

/**
 * @Author liaohong
 * @create 2020/9/28 9:45
 */
@Data
public class ProjectVo {

    @Schema(description = "项目Id")
    public int id;

    @Schema(description = "项目编码")
    public String code;

    @Schema(description = "项目名")
    public String name;

    @Schema(description = "客户Id")
    public int customerId;

    @Schema(description = "业务品种")
    public String businessType;

    @Schema(description = "金额")
    public long amount;

    @Schema(description = "期限")
    public int during;

    @Schema(description = "期限单位")
    public int duringUnit;

    @Schema(description = "项目状态")
    public String ibcStatus;

    @Schema(description = "项目状态(Int)")
    public int ibcStatusInt;

    @Schema(description = "申请日期")
    public LocalDate createDate;


    @Schema(description = "企业类型")
    public String enterprisesType;

    @Schema(description = "合同年份")
    public String contYear;

    @Schema(description = "合同字号")
    public String contNo;

    public void copyProject(ProjectInfo project) {
        this.id = project.getId();
        this.code = project.getCode();
        this.name = project.getName();
        this.customerId = project.getCustomerId();
        this.businessType = project.getBusinessTypeName();
        this.amount = project.getAmount();
        this.during = project.getDuring();
        this.duringUnit = project.getDuringUnit();
        this.ibcStatus = project.getIbcStatus().name();
        this.ibcStatusInt = project.getIbcStatus().ordinal();
        this.createDate = project.getCreateAt().toLocalDate();
        this.enterprisesType = project.getEnterprisesType() == null ? "" : project.getEnterprisesType().getCnName();
        this.contYear = project.getContYear() == 0 ? "" : String.valueOf(project.getContYear());
        this.contNo = project.getContNoDigital() == 0 ? "" : project.getContNoFormat();
    }
}
