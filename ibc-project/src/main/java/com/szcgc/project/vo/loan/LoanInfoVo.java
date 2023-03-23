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
public class LoanInfoVo {

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "放款金额")
    private BigDecimal amount;

    @Schema(description = "期限")
    private String expires;

    @Schema(description = "拟放款日期")
    private String loanDate;

    @Schema(description = "放款日期")
    private String loanStartDate;

    @Schema(description = "到期日期")
    private String loanEndDate;

    @Schema(description = "放款状态")
    private Integer loanStatus;


}
