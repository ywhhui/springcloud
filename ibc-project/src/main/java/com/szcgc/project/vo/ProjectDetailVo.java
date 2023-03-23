package com.szcgc.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

/**
 * @Author liaohong
 * @create 2022/10/8 10:04
 */
@Data
public class ProjectDetailVo extends ProjectSupervisorVo {


    @Schema(description = "操作主体")
    public String operateObject;

    @Schema(description = "签约日期")
    public LocalDate signDate;

    @Schema(description = "签约金额")
    public long signAmt;

    @Schema(description = "借款合同号")
    public String loanAgreementCode;

    @Schema(description = "保证合同号")
    public String guaranteeAgreementCode;

    @Schema(description = "委托保证合同号")
    public String delegateAgreementCode;

    @Schema(description = "承保金额")
    public long guaranteeAmt;

    @Schema(description = "开始日期")
    public LocalDate guaranteeDate;

    @Schema(description = "结束日期")
    public LocalDate guaranteeEndDate;

    @Schema(description = "还款金额")
    public long repayAmt;

    @Schema(description = "在保金额")
    public long ongoingAmt;
}
