package com.szcgc.finance.model.analysis;

import com.szcgc.finance.model.FactorInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * 财务分析主表
 *
 * @author chenjiaming
 * @date 2022-9-27 09:34:41
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "财务分析主表")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_finance_analysis", schema = "gmis_customer")
public class FinanceAnalysis extends FactorInfo {

    @Schema(name = "分析记录名", required = true)
    private String analysisName;

    @Transient
    @Schema(description = "基本财务数据简表")
    private List<BaseFinanceDataSimple> baseFinanceDatumSimples;

    @Transient
    @Schema(description = "财务分析简表")
    private List<FinanceAnalysisSimple> financeAnalysisSimples;

    @Transient
    @Schema(description = "资产负债简表")
    private List<BalanceSheetSimple> balanceSheetSimples;

    @Transient
    @Schema(description = "损益简表")
    private List<IncomeStatementSimple> incomeStatementSimples;

    @Transient
    @Schema(description = "现金流量简表")
    private List<CashFlowStatementSimple> cashFlowStatementSimples;

}
