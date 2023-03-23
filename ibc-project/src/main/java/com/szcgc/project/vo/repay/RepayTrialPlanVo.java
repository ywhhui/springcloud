package com.szcgc.project.vo.repay;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 还款试算计划vo
 *
 * @author chenjiaming
 * @date 2022-11-15 08:54:41
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RepayTrialPlanVo {

    @Schema(description = "日期")
    private String date;

    @Schema(description = "期次")
    private Integer period;

    @Schema(description = "应还本金")
    private BigDecimal shouldRepayCapital;

    @Schema(description = "应还利息")
    private BigDecimal shouldRepayAccrual;

    @Schema(description = "应还服务费")
    private BigDecimal shouldRepayServiceAmount;

    @Schema(description = "应还合计")
    private BigDecimal shouldRepayCount;

    @Schema(description = "剩余本金")
    private BigDecimal residueCapital;

    @Schema(description = "创收金额")
    private BigDecimal createIncome;

    @Schema(description = "是否手工账单")
    private Boolean handmadeBill;

}
