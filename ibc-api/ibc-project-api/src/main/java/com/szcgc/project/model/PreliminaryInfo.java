package com.szcgc.project.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author; liaohong
 * @date: 2021/6/18 9:33
 * @description: 受理
 */
@Data
@Entity
@Table(name = "preliminaryinfo", schema = "gmis_project")
public class PreliminaryInfo {

    @Id
    @Column(name = "id", length = 11)
    private int id;

    @Schema(description = "客户Id(从历史客户受理进来需要传该字段)")
    private int customerId;

    @Schema(description = "客户名称")
    private String name;
    @Schema(description = "客户类型")
    private int cate;
    @Schema(description = "业务品种")
    private String businessType;
    @Schema(description = "申请金额")
    private long amount;
    @Schema(description = "申请期限")
    private int during;
    @Schema(description = "期限单位(1天 2月 3年)")
    private int duringUnit;

    @Schema(description = "统一社会信用代码")
    private String customerCode;
    @Schema(description = "法人")
    private String legalPerson;
    @Schema(description = "法人国籍")
    private String legalNation;

    @Schema(description = "证件类型")
    private String certifyCate;
    @Schema(description = "证件号码")
    private String certifyCode;

    @Schema(description = "联系人")
    private String contactPerson;
    @Schema(description = "联系电话")
    private String contactPhone;

    @Schema(description = "创建人")
    @Column(updatable = false)
    private int createBy;

    @Schema(description = "创建时间")
    @Column(updatable = false)
    private LocalDateTime createAt;

}
