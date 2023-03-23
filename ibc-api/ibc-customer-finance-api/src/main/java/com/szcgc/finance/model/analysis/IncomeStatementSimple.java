package com.szcgc.finance.model.analysis;

import com.szcgc.finance.annotation.Excel;
import com.szcgc.finance.model.FactorInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 损益简表
 *
 * @author chenjiaming
 * @date 2022-9-27 10:01:41
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "损益简表")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_finance_income_statement_simple", schema = "gmis_customer")
public class IncomeStatementSimple extends FactorInfo {

    @Schema(description = "主表id")
    private Integer mainId;

    @Schema(description = "期次,1前年,2去年,3去年同期,4今年")
    private Integer period;

    @Schema(description = "年月,yyyy-MM格式")
    private String date;

    @Excel
    @Schema(description = "收入")
    private String b01;

    @Excel
    @Schema(description = "成本税金")
    private String ct;

    @Excel
    @Schema(description = "期间费用")
    private String pc ;

    @Excel
    @Schema(description = "其他业务利润")
    private String oop;

    @Excel
    @Schema(description = "外收净额")
    private String noi;

    @Excel
    @Schema(description = "投资收益")
    private String ii ;

    @Excel
    @Schema(description = "所得税")
    private String it ;

    @Excel
    @Schema(description = "净利润")
    private String nep;

    @Excel
    @Schema(description = "毛利率")
    private String gpr;

    @Excel
    @Schema(description = "净利率")
    private String nir;

}
