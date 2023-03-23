package com.szcgc.finance.dto;

import com.szcgc.finance.model.base.BalanceSheet;
import com.szcgc.finance.model.base.CashFlowStatement;
import com.szcgc.finance.model.base.IncomeStatement;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "财务数据对象")
public class FinanceDto {

    @Schema(description = "资产负债表")
    private BalanceSheet balanceSheet;

    @Schema(description = "损益表")
    private IncomeStatement incomeStatement;

    @Schema(description = "现金流量表")
    private CashFlowStatement cashFlowStatement;

}
