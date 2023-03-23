package com.szcgc.finance.model.analysis;

import com.szcgc.finance.annotation.Excel;
import com.szcgc.finance.model.FactorInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 资产负债表简表
 *
 * @author chenjiaming
 * @date 2022-9-27 09:53:47
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "资产负债表简表")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_finance_balance_sheet_simple", schema = "gmis_customer")
public class BalanceSheetSimple extends FactorInfo {

    @Schema(description = "主表id")
    private Integer mainId;

    @Schema(description = "期次,1前年,2去年,3去年同期,4今年")
    private Integer period;

    @Schema(description = "年月,yyyy-MM格式")
    private String date;

    @Excel
    @Schema(description = "货币资金")
    private String cce;

    @Excel
    @Schema(description = "短期投资")
    private String sti;

    @Excel
    @Schema(description = "应收票据")
    private String a0e;

    @Excel
    @Schema(description = "应收帐款")
    private String a0g;

    @Excel
    @Schema(description = "预付帐款")
    private String a0i;

    @Excel
    @Schema(description = "其他应收")
    private String onr;

    @Excel
    @Schema(description = "存货")
    private String fg;

    @Excel
    @Schema(description = "待摊")
    private String dpe;

    @Excel
    @Schema(description = "长期投资")
    private String lti;

    @Excel
    @Schema(description = "固定资产")
    private String fa;

    @Excel
    @Schema(description = "无形资产及其他资产")
    private String ia;

    @Excel
    @Schema(description = "总资产")
    private String a2z;

    @Excel
    @Schema(description = "借款")
    private String slb;

    @Excel
    @Schema(description = "应付票据")
    private String a35;

    @Excel
    @Schema(description = "应付帐款")
    private String a37;

    @Excel
    @Schema(description = "预收帐款")
    private String a39;

    @Excel
    @Schema(description = "应交税金")
    private String a3d;

    @Excel
    @Schema(description = "其他应付")
    private String op;

    @Excel
    @Schema(description = "实收资本")
    private String puc;

    @Excel
    @Schema(description = "资本公积")
    private String cr;

    @Excel
    @Schema(description = "留存收益")
    private String sr;

    @Excel
    @Schema(description = "少数股东权益")
    private String poe;

    @Excel
    @Schema(description = "负债及权益")
    private String loe;

}
