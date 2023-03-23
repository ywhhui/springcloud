package com.szcgc.finance.model;

import com.szcgc.finance.annotation.Excel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 资信评分表
 *
 * @author chenjiaming
 * @date 2022-10-20 09:49:03
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "资信评分表")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_finance_credit_rating", schema = "gmis_customer")
public class CreditRating extends FactorInfo {

    @Schema(description = "年月,yyyy-MM格式", required = true)
    private String date;

    @Excel
    @Schema(description = "以往贷款偿还记录")
    private Integer q01;

    @Excel
    @Schema(description = "以往利息支付记录")
    private Integer q02;

    @Excel
    @Schema(description = "以往纳税情况")
    private Integer q03;

    @Excel
    @Schema(description = "在担保中心信用记录")
    private Integer q04;

    @Excel
    @Schema(description = "综合")
    private Integer q05;

    @Excel
    @Schema(description = "帐、表、证、物相符程度")
    private Integer q06;

    @Excel
    @Schema(description = "财务经理学历水平")
    private Integer q07;

    @Excel
    @Schema(description = "财务经理所学专业")
    private Integer q08;

    @Excel
    @Schema(description = "财务经理从事财务管理时间")
    private Integer q09;

    @Excel
    @Schema(description = "财务经理在本企业从事财务管理时间")
    private Integer q10;

    @Excel
    @Schema(description = "企业财务管理规范评价")
    private Integer q11;

    @Excel
    @Schema(description = "对企业现金流量评价")
    private Integer q12;

    @Excel
    @Schema(description = "领导人平均学历")
    private Integer q13;

    @Excel
    @Schema(description = "领导人行业经验")
    private Integer q14;

    @Excel
    @Schema(description = "业绩评价")
    private Integer q15;

    @Excel
    @Schema(description = "领导人信用状况")
    private Integer q16;

    @Excel
    @Schema(description = "领导班子素质综合评价")
    private Integer q17;

    @Excel
    @Schema(description = "技术先进程度、设备性能")
    private Integer q18;

    @Excel
    @Schema(description = "产品市场状况及前景")
    private Integer q19;

    @Excel
    @Schema(description = "人才条件")
    private Integer q20;

    @Excel
    @Schema(description = "同行业竞争状况")
    private Integer q21;

    @Excel
    @Schema(description = "营销水平")
    private Integer q22;

    @Excel
    @Schema(description = "综合评价")
    private Integer q23;

}
