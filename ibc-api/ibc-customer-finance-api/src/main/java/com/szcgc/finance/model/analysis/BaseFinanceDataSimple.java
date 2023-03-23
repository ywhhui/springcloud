package com.szcgc.finance.model.analysis;

import com.szcgc.finance.annotation.Excel;
import com.szcgc.finance.model.FactorInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 基本财务数据简表
 *
 * @author chenjiaming
 * @date 2022-9-27 09:00:46
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "基本财务数据简表")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_finance_base_finance_data_simple", schema = "gmis_customer")
public class BaseFinanceDataSimple extends FactorInfo {

    @Schema(description = "主表id")
    private Integer mainId;

    @Schema(description = "期次,1前年,2去年,3去年同期,4今年")
    private Integer period;

    @Schema(description = "年月,yyyy-MM格式")
    private String date;

    @Excel
    @Schema(description = "总资产")
    private String ta;

    @Excel
    @Schema(description = "净资产")
    private String na;

    @Excel
    @Schema(description = "销售额")
    private String oi;

    @Excel
    @Schema(description = "利润总额")
    private String gp;

    @Excel
    @Schema(description = "净利润")
    private String ntp;

}
