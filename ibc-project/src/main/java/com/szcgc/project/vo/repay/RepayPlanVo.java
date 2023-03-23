package com.szcgc.project.vo.repay;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 还款计划vo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RepayPlanVo {

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "项目Id")
    private Integer projectId;

    @Schema(description = "客户id")
    private Integer customerId;

    @Schema(description = "放款id")
    private Integer loanId;

    @Schema(description = "期次")
    private Integer period;

    @Schema(description = "应还日期")
    private String shouldRepayDate;

    @Schema(description = "应还本金")
    private BigDecimal shouldRepayCapital;

    @Schema(description = "应还利息")
    private BigDecimal shouldRepayAccrual;

    @Schema(description = "实际还款日期")
    private String realRepayDate;

    @Schema(description = "本次实还本金")
    private BigDecimal realRepayCapital;

    @Schema(description = "本次实还利息")
    private BigDecimal realRepayAccrual;

    @Schema(description = "是否入账")
    private Boolean credited;

}
