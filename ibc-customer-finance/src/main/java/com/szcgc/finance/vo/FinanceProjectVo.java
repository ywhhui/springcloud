package com.szcgc.finance.vo;

import com.szcgc.finance.model.base.BalanceSheet;
import com.szcgc.finance.model.base.CashFlowStatement;
import com.szcgc.finance.model.base.IncomeStatement;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 带财报数据的项目
 *
 * @author chenjiaming
 * @date 2022-10-14 11:52:13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinanceProjectVo {

    private Integer id;

    @Schema(description = "项目编码")
    private String code;

    @Schema(description = "资产负债表")
    private List<BalanceSheet> balanceSheetList;

    @Schema(description = "现金流量表")
    private List<CashFlowStatement> cashFlowStatementList;

    @Schema(description = "损益表")
    private List<IncomeStatement> incomeStatementList;

}
