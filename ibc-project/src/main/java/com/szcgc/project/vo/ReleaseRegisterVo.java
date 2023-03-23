package com.szcgc.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 解保登记提交 需要的参数
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseRegisterVo {

    @Schema(name = "项目Id")
    private int projectId;

    /**
     * 任务id
     */
    private String taskId;

    @Schema(description = "还款日期")
    private String repaymentDate;

    @Schema(description = "解保金额(元)")
    private BigDecimal releaseAmt;

    @Schema(description = "解保说明")
    private String releaseDescribe;

    /** 户名 */
    private String releaseAccountName;

    /** 开户行编号 */
    private String openBankNo;

    /** 开户行名称 */
    private String openBankName;

    /** 退回银行账号 */
    private String returnBankAccount;

    @Schema(description = "意见")
    private String confirmOpinion;

}
