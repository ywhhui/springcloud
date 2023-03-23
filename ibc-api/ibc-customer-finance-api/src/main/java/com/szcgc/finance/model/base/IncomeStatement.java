package com.szcgc.finance.model.base;

import com.szcgc.finance.annotation.Excel;
import com.szcgc.finance.model.FactorInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 损益表
 *
 * @author chenjiaming
 * @date 2022-9-22 21:51:16
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "损益表")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_finance_income_statement", schema = "gmis_customer")
public class IncomeStatement extends FactorInfo {

    @Schema(description = "年月", required = true)
    private String date;

    @Excel
    @Schema(description = "一、营业收入")
    private BigDecimal b01;

    @Excel
    @Schema(description = "减：营业成本")
    private BigDecimal b02;

    @Excel
    @Schema(description = "    营业税金及附加")
    private BigDecimal b03;

    @Excel(formula = "${colIdx}${rowIdx-3}-${colIdx}${rowIdx-2}-${colIdx}${rowIdx-1}", edit = false)
    @Schema(description = "主营业务利润（亏损以\"-\"号填列）")
    private BigDecimal b04;

    @Excel
    @Schema(description = "减：销售费用")
    private BigDecimal b0a;

    @Excel
    @Schema(description = "    管理费用")
    private BigDecimal b0c;

    @Excel
    @Schema(description = "    财务费用")
    private BigDecimal b0e;

    @Excel
    @Schema(description = "资产减值损失")
    private BigDecimal b0g;

    @Excel
    @Schema(description = "加：公允价值变动收益（亏损以\"-\"号填列）")
    private BigDecimal b0i;

    @Excel
    @Schema(description = "加：投资收益（亏损以\"-\"号填列）")
    private BigDecimal b0k;

    @Excel
    @Schema(description = "其中：对联营企业和合营企业的投资")
    private BigDecimal b0m;

    @Excel(formula = "${colIdx}${rowIdx-8}-SUM(${colIdx}${rowIdx-7}:${colIdx}${rowIdx-4})+${colIdx}${rowIdx-3}+${colIdx}${rowIdx-2}", edit = false)
    @Schema(description = "二、营业利润（亏损以\"-\"号填列）")
    private BigDecimal b1a;

    @Excel
    @Schema(description = "加：营业外收入")
    private BigDecimal b1c;

    @Excel
    @Schema(description = "其他业务利润")
    private BigDecimal b1e;

    @Excel
    @Schema(description = "    补贴收入")
    private BigDecimal b1g;

    @Excel
    @Schema(description = "减：营业外支出")
    private BigDecimal b1i;

    @Excel
    @Schema(description = "其中：非流动资产处置损失")
    private BigDecimal b1k;

    @Excel(formula = "SUM(${colIdx}${rowIdx-6}:${colIdx}${rowIdx-3})-${colIdx}${rowIdx-2}", edit = false)
    @Schema(description = "三、利润总额（亏损以\"-\"号填列）")
    private BigDecimal b2a;

    @Excel
    @Schema(description = "减：所得税费用")
    private BigDecimal b2c;

    @Excel
    @Schema(description = "少数所有者（股东）权益")
    private BigDecimal b2e;

    @Excel
    @Schema(description = "未弥补子公司亏损")
    private BigDecimal b2g;

    @Excel(formula = "${colIdx}${rowIdx-4}-SUM(${colIdx}${rowIdx-3}:${colIdx}${rowIdx-1})", edit = false)
    @Schema(description = "四、净利润")
    private BigDecimal b3a;

    @Excel
    @Schema(description = "年初未分配利润")
    private BigDecimal b3c;

    @Excel
    @Schema(description = "其他转入")
    private BigDecimal b3e;

    @Excel(formula = "SUM(${colIdx}${rowIdx-3}:${colIdx}${rowIdx-1})", edit = false)
    @Schema(description = "可供分配利润")
    private BigDecimal b3g;

    @Excel
    @Schema(description = "提取法定盈余公积")
    private BigDecimal b3i;

    @Excel
    @Schema(description = "提取法定公益金")
    private BigDecimal b3k;

    @Excel
    @Schema(description = "提取福利及奖励基金")
    private BigDecimal b3m;

    @Excel(formula = "${colIdx}${rowIdx-4}-SUM(${colIdx}${rowIdx-3}:${colIdx}${rowIdx-1})", edit = false)
    @Schema(description = "可供股东分配的利润")
    private BigDecimal b3p;

    @Excel
    @Schema(description = "应付优先股股利")
    private BigDecimal b3r;

    @Excel
    @Schema(description = "提取任意盈余公积")
    private BigDecimal b3t;

    @Excel
    @Schema(description = "应付普通股股利")
    private BigDecimal b3v;

    @Excel
    @Schema(description = "转作股本的普通股股利")
    private BigDecimal b3x;

    @Excel(formula = "${colIdx}${rowIdx-5}-SUM(${colIdx}${rowIdx-4}:${colIdx}${rowIdx-1})", edit = false)
    @Schema(description = "未分配利润")
    private BigDecimal b3z;

    @Excel
    @Schema(description = "归属于母公司股东净利润")
    private BigDecimal b41;

    @Excel(formula = "${colIdx}${rowIdx-14}-${colIdx}${rowIdx-1}", edit = false)
    @Schema(description = "少数股东净利润")
    private BigDecimal b43;

    @Excel
    @Schema(description = "五、每股收益")
    private BigDecimal b4a;

    @Excel
    @Schema(description = "(一)每股收益")
    private BigDecimal b4c;

    @Excel
    @Schema(description = "(二)稀释每股收益")
    private BigDecimal b4e;

    @Excel
    @Schema(description = "六、其他综合收益")
    private BigDecimal b4g;

    @Excel(formula = "${colIdx}${rowIdx-19}+${colIdx}${rowIdx-1}", edit = false)
    @Schema(description = "七、综合收益总额")
    private BigDecimal b4i;

    @Excel
    @Schema(description = "归属于母公司股东综合收益总额")
    private BigDecimal b4k;

    @Excel(formula = "${colIdx}${rowIdx-2}-${colIdx}${rowIdx-1}", edit = false)
    @Schema(description = "少数股东综合收益总额")
    private BigDecimal b4m;

}
