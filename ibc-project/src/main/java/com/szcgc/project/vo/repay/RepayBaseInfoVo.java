package com.szcgc.project.vo.repay;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 还款计划基本信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RepayBaseInfoVo {

    @Schema(description = "项目Id")
    private Integer projectId;

    @Schema(description = "项目编号")
    private String projectCode;

    @Schema(description = "客户id")
    private Integer customerId;

    @Schema(description = "客户名称")
    private String customerName;

    @Schema(description = "项目经理A")
    private String roleA;

    @Schema(description = "放款id")
    private Integer loanId;

    @Schema(description = "放款编号")
    private String loanCode;

    @Schema(description = "放款金额")
    private BigDecimal loanAmount;

    @Schema(description = "剩余本金")
    private BigDecimal residueAmount;

    @Schema(description = "放款日期")
    private String loanDate;


}
