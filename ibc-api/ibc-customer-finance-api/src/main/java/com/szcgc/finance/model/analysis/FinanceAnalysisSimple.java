package com.szcgc.finance.model.analysis;

import com.szcgc.finance.annotation.Excel;
import com.szcgc.finance.model.FactorInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 财务分析简表
 *
 * @author chenjiaming
 * @date 2022-9-27 09:34:41
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "财务分析简表")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_finance_analysis_simple", schema = "gmis_customer")
public class FinanceAnalysisSimple extends FactorInfo {

    @Schema(description = "主表id")
    private Integer mainId;

    @Schema(description = "期次,1前年,2去年,3去年同期,4今年")
    private Integer period;

    @Schema(description = "年月,yyyy-MM格式")
    private String date;

    @Excel
    @Schema(description = "总资产")
    private String ta;

    @Excel
    @Schema(description = "营运资金")
    private String of;

    @Excel
    @Schema(description = "净资产")
    private String na;

    @Excel(trunUnit = false)
    @Schema(description = "资产负债率 ")
    private String alr;

    @Excel(trunUnit = false)
    @Schema(description = "流动比率 ")
    private String lr;

    @Excel(trunUnit = false)
    @Schema(description = "速动比率 ")
    private String qr;

    @Excel(trunUnit = false)
    @Schema(description = "长期资产适宜率")
    private String ltr;

    @Excel(trunUnit = false)
    @Schema(description = "齿轮比率")
    private String gr;

    @Excel(trunUnit = false)
    @Schema(description = "或有负债比率")
    private String clr;

    @Excel(trunUnit = false)
    @Schema(description = "贷款按期偿还率 ")
    private String lrs;

    @Excel
    @Schema(description = "年营业收入")
    private String ar;

    @Excel(trunUnit = false)
    @Schema(description = "销售利润率")
    private String sir;

    @Excel(trunUnit = false)
    @Schema(description = "应收帐款周转率(次)")
    private String art;

    @Excel(trunUnit = false)
    @Schema(description = "存货周转率(次)")
    private String tri;

    @Excel(trunUnit = false)
    @Schema(description = "净资产回报率")
    private String rna;

    @Excel(trunUnit = false)
    @Schema(description = "利息保障倍数")
    private String mig;

    @Excel(trunUnit = false)
    @Schema(description = "净资产增长率")
    private String nagr;

    @Excel(trunUnit = false)
    @Schema(description = "销售收入增长率")
    private String gsr;

    @Excel(trunUnit = false)
    @Schema(description = "利润增长率")
    private String pgr;

    @Excel(trunUnit = false)
    @Schema(description = "利润增长额")
    private String pg;

    @Excel(trunUnit = false)
    @Schema(description = "现金流入量/流动负债")
    private String ciil;

    @Excel(trunUnit = false)
    @Schema(description = "现金流入量/负债总额")
    private String citi;

    @Excel(trunUnit = false)
    @Schema(description = "现金流入量/主营收入")
    private String cimi;

    @Excel(trunUnit = false)
    @Schema(description = "现金流量净额/流动负债")
    private String ncil;

    @Excel(trunUnit = false)
    @Schema(description = "现金流量净额/负债总额")
    private String ncti;

    @Excel(trunUnit = false)
    @Schema(description = "现金流量净额/主营收入")
    private String ncmi;

}
