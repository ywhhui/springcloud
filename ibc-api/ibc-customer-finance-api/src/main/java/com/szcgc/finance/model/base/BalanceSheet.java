package com.szcgc.finance.model.base;

import com.szcgc.finance.annotation.Excel;
import com.szcgc.finance.model.FactorInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 资产负债表
 *
 * @author chenjiaming
 * @date 2022-9-22 21:51:16
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "资产负债表")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_finance_balance_sheet", schema = "gmis_customer")
public class BalanceSheet extends FactorInfo {

    @Schema(description = "年月,yyyy-MM格式", required = true)
    private String date;

    @Excel
    @Schema(description = "货币资金")
    private BigDecimal a01;

    @Excel
    @Schema(description = "交易性金融资产")
    private BigDecimal a0a;

    @Excel
    @Schema(description = "短期投资")
    private BigDecimal a0c;

    @Excel
    @Schema(description = "应收票据")
    private BigDecimal a0e;

    @Excel
    @Schema(description = "应收账款")
    private BigDecimal a0g;

    @Excel
    @Schema(description = "预付账款")
    private BigDecimal a0i;

    @Excel
    @Schema(description = "应收利息")
    private BigDecimal a0k;

    @Excel
    @Schema(description = "应收股利")
    private BigDecimal a0m;

    @Excel
    @Schema(description = "其他应收款")
    private BigDecimal a0p;

    @Excel
    @Schema(description = "应收补贴款")
    private BigDecimal a0r;

    @Excel
    @Schema(description = "存货")
    private BigDecimal a0t;

    @Excel
    @Schema(description = "待摊费用")
    private BigDecimal a0v;

    @Excel
    @Schema(description = "一年内到期的长期债权投资")
    private BigDecimal a0w;

    @Excel
    @Schema(description = "一年内到期的非流动资产")
    private BigDecimal a0x;

    @Excel
    @Schema(description = "其他流动资产")
    private BigDecimal a0y;

    @Excel(formula = "SUM(${colIdx}${rowIdx-15}:${colIdx}${rowIdx-1})", edit = false)
    @Schema(description = "流动资产合计")
    private BigDecimal a0z;

    @Excel
    @Schema(description = "可供出售的金融资产")
    private BigDecimal a10;

    @Excel
    @Schema(description = "持有至到期的投资")
    private BigDecimal a11;

    @Excel
    @Schema(description = "长期应收款")
    private BigDecimal a12;

    @Excel
    @Schema(description = "投资性房地产")
    private BigDecimal a13;

    @Excel
    @Schema(description = "长期股权投资")
    private BigDecimal a14;

    @Excel
    @Schema(description = "长期债权投资")
    private BigDecimal a16;

    @Excel(formula = "${colIdx}${rowIdx-2}+${colIdx}${rowIdx-1}", edit = false)
    @Schema(description = "长期投资合计")
    private BigDecimal a17;

    @Excel
    @Schema(description = "其中：合并价差")
    private BigDecimal a18;

    @Excel(formula = "${colIdx}${rowIdx-2}-${colIdx}${rowIdx-1}", edit = false)
    @Schema(description = "股权投资差额")
    private BigDecimal a19;

    @Excel
    @Schema(description = "固定资产原价")
    private BigDecimal a1a;

    @Excel
    @Schema(description = "  减：累计折旧")
    private BigDecimal a1c;

    @Excel(formula = "${colIdx}${rowIdx-2}-${colIdx}${rowIdx-1}", edit = false)
    @Schema(description = "固定资产净值")
    private BigDecimal a1e;

    @Excel
    @Schema(description = "  减：固定资产减值准备")
    private BigDecimal a1g;

    @Excel(formula = "${colIdx}${rowIdx-2}-${colIdx}${rowIdx-1}", edit = false)
    @Schema(description = "固定资产净额")
    private BigDecimal a1i;

    @Excel
    @Schema(description = "在建工程")
    private BigDecimal a1k;

    @Excel
    @Schema(description = "工程物资")
    private BigDecimal a1m;

    @Excel
    @Schema(description = "固定资产清理")
    private BigDecimal a1p;

    @Excel(formula = "SUM(${colIdx}${rowIdx-4}:${colIdx}${rowIdx-1})", edit = false)
    @Schema(description = "固定资产合计")
    private BigDecimal a1r;

    @Excel
    @Schema(description = "生产性生物资产")
    private BigDecimal a2a;

    @Excel
    @Schema(description = "油气资产")
    private BigDecimal a2c;

    @Excel
    @Schema(description = "无形资产")
    private BigDecimal a2e;

    @Excel
    @Schema(description = "开发支出")
    private BigDecimal a2g;

    @Excel
    @Schema(description = "商誉")
    private BigDecimal a2i;

    @Excel
    @Schema(description = "长期待摊费用")
    private BigDecimal a2k;

    @Excel
    @Schema(description = "递延所得税资产(款借项)")
    private BigDecimal a2m;

    @Excel
    @Schema(description = "其他非流动(长期)资产")
    private BigDecimal a2p;

    @Excel(formula = "SUM(${colIdx}${rowIdx-9}:${colIdx}${rowIdx-1})+SUM(${colIdx}${rowIdx-26}:${colIdx}${rowIdx-23})+${colIdx}${rowIdx-18}", edit = false)
    @Schema(description = "非流动资产合计")
    private BigDecimal a2r;

    @Excel(formula = "${colIdx}${rowIdx-28}+${colIdx}${rowIdx-1}", edit = false)
    @Schema(description = "资产总计")
    private BigDecimal a2z;

    @Excel
    @Schema(description = "短期借款")
    private BigDecimal a31;

    @Excel
    @Schema(description = "交易性金融负债")
    private BigDecimal a33;

    @Excel
    @Schema(description = "应付票据")
    private BigDecimal a35;

    @Excel
    @Schema(description = "应付账款")
    private BigDecimal a37;

    @Excel
    @Schema(description = "预收账款(款项)")
    private BigDecimal a39;

    @Excel
    @Schema(description = "应付工资(职工薪酬)")
    private BigDecimal a3b;

    @Excel
    @Schema(description = "应交税金(费)")
    private BigDecimal a3d;

    @Excel
    @Schema(description = "应付利息")
    private BigDecimal a3f;

    @Excel
    @Schema(description = "应付股利")
    private BigDecimal a3h;

    @Excel
    @Schema(description = "其他应付款")
    private BigDecimal a3j;

    @Excel
    @Schema(description = "一年内到期的长期(非流动)负债")
    private BigDecimal a3m;

    @Excel
    @Schema(description = "其他流动负债")
    private BigDecimal a3p;

    @Excel
    @Schema(description = "应付福利费")
    private BigDecimal a3r;

    @Excel
    @Schema(description = "其他应交款")
    private BigDecimal a3t;

    @Excel
    @Schema(description = "预提费用")
    private BigDecimal a3v;

    @Excel(formula = "SUM(${colIdx}${rowIdx-15}:${colIdx}${rowIdx-1})", edit = false)
    @Schema(description = "流动负债合计")
    private BigDecimal a3z;

    @Excel
    @Schema(description = "长期借款")
    private BigDecimal a41;

    @Excel
    @Schema(description = "应付债券")
    private BigDecimal a43;

    @Excel
    @Schema(description = "长期应付款")
    private BigDecimal a45;

    @Excel
    @Schema(description = "专项应付款")
    private BigDecimal a4a;

    @Excel
    @Schema(description = "预计负债")
    private BigDecimal a4c;

    @Excel
    @Schema(description = "递延税款贷项(所得税负债)")
    private BigDecimal a4e;

    @Excel
    @Schema(description = "其他长期(非流动)负债")
    private BigDecimal a4g;

    @Excel(formula = "SUM(${colIdx}${rowIdx-7}:${colIdx}${rowIdx-1})", edit = false)
    @Schema(description = "长期(非流动)负债合计")
    private BigDecimal a4j;

    @Excel(formula = "${colIdx}${rowIdx-9}+${colIdx}${rowIdx-1}", edit = false)
    @Schema(description = "负债合计")
    private BigDecimal a4z;

    @Excel
    @Schema(description = "实收资本（或股本）")
    private BigDecimal a51;

    @Excel
    @Schema(description = "资本公积")
    private BigDecimal a53;

    @Excel
    @Schema(description = "减：库存股")
    private BigDecimal a55;

    @Excel
    @Schema(description = "盈余公积")
    private BigDecimal a57;

    @Excel
    @Schema(description = "  其中：法定公益金")
    private BigDecimal a59;

    @Excel
    @Schema(description = "未分配利润")
    private BigDecimal a5b;

    @Excel
    @Schema(description = "外币折算差额")
    private BigDecimal a5d;

    @Excel
    @Schema(description = "累积未弥补子公司亏损")
    private BigDecimal a5f;

    @Excel(formula = "SUM(${colIdx}${rowIdx-3}:${colIdx}${rowIdx-1})+${colIdx}${rowIdx-8}+${colIdx}${rowIdx-7}+${colIdx}${rowIdx-5}", edit = false)
    @Schema(description = "归属母公司股东的权益")
    private BigDecimal a5h;

    @Excel
    @Schema(description = "少数股东权益")
    private BigDecimal a5j;

    @Excel(formula = "${colIdx}${rowIdx-2}+${colIdx}${rowIdx-1}", edit = false)
    @Schema(description = "所有者权益(或股东权益)合计")
    private BigDecimal a5x;

    @Excel(formula = "${colIdx}${rowIdx-12}+${colIdx}${rowIdx-1}", edit = false)
    @Schema(description = "负债和所有者(股本)权益总计")
    private BigDecimal a5z;

    @Excel
    @Schema(description = "或有负债")
    private BigDecimal a91;

    @Excel
    @Schema(description = "逾期贷款")
    private BigDecimal a92;

    @Excel
    @Schema(description = "应还贷款")
    private BigDecimal a93;

}
