package com.szcgc.customer.model.manufacture;

import com.szcgc.customer.annotation.Excel;
import com.szcgc.customer.constant.DicTypeEnum;
import com.szcgc.customer.model.base.FactorInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 水电煤用量核实
 *
 * @author chenjiaming
 * @date 2022-9-22 17:09:35
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "水电煤用量核实")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_cust_usage_amount", schema = "gmis_customer")
public class UsageAmount extends FactorInfo {

    @Excel(sample = "2022")
    @Schema(description = "统计年份", required = true)
    private String year;

    @Excel(sample = "06")
    @Schema(description = "统计月份", required = true)
    private String month;

    @Excel(sample = "***")
    @Schema(description = "用水数据", required = true)
    private String water;

    @Excel(sample = "188.88")
    @Schema(description = "用水应缴费用(万元)")
    private BigDecimal waterFee;

    @Excel(sample = "188.88")
    @Schema(description = "用水实缴费用(万元)")
    private BigDecimal waterFeeReal;

    @Excel(sample = "188.88")
    @Schema(description = "用水欠缴费用(万元)")
    private BigDecimal waterArrearage;

    @Excel(sample = "***")
    @Schema(description = "用电数据", required = true)
    private String electricity;

    @Excel(sample = "188.88")
    @Schema(description = "用电应缴费用(万元)")
    private BigDecimal electricityFee;

    @Excel(sample = "188.88")
    @Schema(description = "用电实缴费用(万元)")
    private BigDecimal electricityFeeReal;

    @Excel(sample = "188.88")
    @Schema(description = "用电欠缴费用(万元)")
    private BigDecimal electricityArrearage;

    @Excel(sample = "***")
    @Schema(description = "用煤数据", required = true)
    private String coal;

    @Excel(sample = "188.88")
    @Schema(description = "用煤应缴费用(万元)")
    private BigDecimal coalFee;

    @Excel(sample = "188.88")
    @Schema(description = "用煤实缴费用(万元)")
    private BigDecimal coalFeeReal;

    @Excel(sample = "188.88")
    @Schema(description = "用煤欠缴费用(万元)")
    private BigDecimal coalArrearage;

    @Excel(sample = "是", comboxType = DicTypeEnum.WHETHER)
    @Schema(description = "工资是否按时支付", required = true)
    private Integer wagePay;

    @Excel(sample = "是", comboxType = DicTypeEnum.WHETHER)
    @Schema(description = "房租是否按时支付", required = true)
    private Integer rentPay;

    @Excel(sample = "是", comboxType = DicTypeEnum.WHETHER)
    @Schema(description = "电费是否按时支付", required = true)
    private Integer electricityPay;

    @Excel(sample = "是", comboxType = DicTypeEnum.WHETHER)
    @Schema(description = "水费是否按时支付", required = true)
    private Integer waterPay;

    @Excel(sample = "是", comboxType = DicTypeEnum.WHETHER)
    @Schema(description = "是否已缴纳", required = true)
    private Integer pay;

}
