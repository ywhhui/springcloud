package com.szcgc.project.vo.repay;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 还款试算vo
 *
 * @author chenjiaming
 * @date 2022-11-14 17:39:16
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RepayTrialVo {

    @Schema(description = "试算金额")
    private BigDecimal amount;

    @Schema(description = "年利率")
    private BigDecimal rate;

    @Schema(description = "期限")
    private Integer expires;

    @Schema(description = "期限单位")
    private Integer expiresUnit;

    @Schema(description = "开始时间")
    private String startDate;

    @Schema(description = "到期时间")
    private String endDate;

    @Schema(description = "还本方案")
    private String repayLoan;

    @Schema(description = "每次还款金额")
    private BigDecimal eachRepayAmount;

    @Schema(description = "还息方案")
    private String repayInterest;

    @Schema(description = "还款日")
    private Integer repayDate;

    @Schema(description = "服务费率")
    private BigDecimal serviceRate;

    @Schema(description = "服务费定额")
    private BigDecimal serviceAmount;

    @Schema(description = "资金成本")
    private BigDecimal capitalRegister;

    @Schema(description = "试算计划")
    private List<RepayTrialPlanVo> list;

}
