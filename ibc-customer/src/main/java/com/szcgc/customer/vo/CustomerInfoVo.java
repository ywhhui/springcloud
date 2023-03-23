package com.szcgc.customer.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

/**
 * 客户信息列表vo
 *
 * @author chenjiaming
 * @date 2022-9-29 16:10:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "客户信息列表vo")
@EqualsAndHashCode(callSuper = false)
public class CustomerInfoVo {

    private Integer id;

    @Schema(description = "客户名称", required = true)
    private String name;

    @Schema(description = "客户类型", required = true)
    @Column(updatable = false)
    private Integer cate;

    @Schema(description = "统一社会信用代码/身份证号")
    @Column(length = 50, updatable = false)
    private String idNo;

    @Schema(description = "法定代表人")
    private String legal;

    @Schema(description = "管护人")
    private String custodianName;

    @Schema(description = "管护人id")
    private Integer custodianId;

    @Schema(description = "管护人部门id")
    private Integer custodianDeptId;

    @Schema(description = "管护人部门")
    private String custodianDeptName;

    @Schema(description = "核心企业")
    private String coreCompany;

    @Schema(description = "在保金额(万)")
    private String warrantyAmount;

}
