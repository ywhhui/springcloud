package com.szcgc.finance.vo;

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
public class QuantitativeAnalysisVo {

    @Schema(description = "净资产")
    private String na;

    @Schema(description = "资产负债率 ")
    private String alr;

    @Schema(description = "流动比率 ")
    private String lr;

    @Schema(description = "速动比率 ")
    private String qr;

    @Schema(description = "长期资产适宜率")
    private String ltr;

    @Schema(description = "齿轮比率")
    private String gr;

    @Schema(description = "或有负债比率")
    private String clr;

    @Schema(description = "贷款按期偿还率 ")
    private String lrs;

    @Schema(description = "年营业收入")
    private String ar;

    @Schema(description = "销售利润率")
    private String sir;

    @Schema(description = "应收帐款周转率(次)")
    private String art;

    @Schema(description = "存货周转率(次)")
    private String tri;

    @Schema(description = "净资产回报率")
    private String rna;

    @Schema(description = "利息保障倍数")
    private String mig;

    @Schema(description = "净资产增长率")
    private String nagr;

    @Schema(description = "销售收入增长率")
    private String gsr;

    @Schema(description = "利润增长率")
    private String pgr;

    @Schema(description = "利润增长额")
    private String pg;

}
