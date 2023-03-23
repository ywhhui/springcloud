package com.szcgc.project.vo.loan;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 资金成本登记-放款申请信息
 *
 * @author chenjiaming
 * @date 2022-11-4 09:59:06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplyInfoVo {

    @Schema(description = "项目id")
    private Integer projectId;

    @Schema(description = "项目编号")
    private String projectCode;

    @Schema(description = "客户id")
    private Integer custId;

    @Schema(description = "客户名称")
    private String custName;

    @Schema(description = "评审主体id")
    private Integer reviewSubjectId;

    @Schema(description = "评审主体")
    private String reviewSubject;

    @Schema(description = "融资金额")
    private BigDecimal amount;

    @Schema(description = "期限")
    private Integer expires;

    @Schema(description = "期限单位,详看字典projectDuringUnit")
    private Integer expiresUnit;

    @Schema(description = "年利率")
    private BigDecimal rate;

    @Schema(description = "业务品种")
    private String businessType;

    @Schema(description = "业务标签")
    private String businessTag;

    @Schema(description = "操作主体id")
    private Integer operationSubjectId;

    @Schema(description = "操作主体")
    private String operationSubject;

    @Schema(description = "放款方式")
    private Integer loanWay;

    @Schema(description = "还款方案")
    private Integer loanPaymentSchema;

    @Schema(description = "还息方案")
    private Integer loanInterestSchema;

    @Schema(description = "项目经理A")
    private String roleA;

    @Schema(description = "项目经理A占比")
    private Integer roleARatio;

    @Schema(description = "项目经理B")
    private String roleB;

    @Schema(description = "项目经理B占比")
    private Integer roleBRatio;

}
