package com.szcgc.finance.vo;

import com.szcgc.finance.model.analysis.FinanceAnalysisSimple;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 定量评分数据
 *
 * @author chenjiaming
 * @date 2022-10-21 16:34:16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "定量评分")
@EqualsAndHashCode(callSuper = false)
public class CreditRatingVo {

    @Schema(description = "财务分析简表数据")
    private FinanceAnalysisSimple financeAnalysisSimple;

    @Schema(description = "定量评分")
    private QuantitativeAnalysisVo quantitativeAnalysisVo;

}
