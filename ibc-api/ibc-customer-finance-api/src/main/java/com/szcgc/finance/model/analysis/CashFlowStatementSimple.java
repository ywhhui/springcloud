package com.szcgc.finance.model.analysis;

import com.szcgc.finance.annotation.Excel;
import com.szcgc.finance.model.FactorInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 现金流量简表
 *
 * @author chenjiaming
 * @date 2022-9-27 10:01:32
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "现金流量简表")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_finance_cash_flow_statement_simple", schema = "gmis_customer")
public class CashFlowStatementSimple extends FactorInfo {

    @Schema(description = "主表id")
    private Integer mainId;

    @Schema(description = "期次,1前年,2去年,3去年同期,4今年")
    private Integer period;

    @Schema(description = "年月,yyyy-MM格式")
    private String date;

    @Excel
    @Schema(description = "经营活动产生的现金流量-流入量")
    private String manageIn;

    @Excel
    @Schema(description = "经营活动产生的现金流量-流出量")
    private String manageOut;

    @Excel
    @Schema(description = "经营活动产生的现金流量-净额")
    private String manageNet;

    @Excel
    @Schema(description = "投资活动产生的现金流量-流入量")
    private String investIn;

    @Excel
    @Schema(description = "投资活动产生的现金流量-流出量")
    private String investOut;

    @Excel
    @Schema(description = "投资活动产生的现金流量-净额")
    private String investNet;

    @Excel
    @Schema(description = "融资活动产生的现金流量-流入量")
    private String financingIn;

    @Excel
    @Schema(description = "融资活动产生的现金流量-流出量")
    private String financingOut;

    @Excel
    @Schema(description = "融资活动产生的现金流量-净额")
    private String financingNet;

    @Excel
    @Schema(description = "合计-流入量")
    private String totalIn;

    @Excel
    @Schema(description = "合计-流出量")
    private String totalOut;

    @Excel
    @Schema(description = "合计-净额")
    private String totalNet;

}
