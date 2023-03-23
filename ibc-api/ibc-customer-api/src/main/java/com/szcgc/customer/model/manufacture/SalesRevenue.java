package com.szcgc.customer.model.manufacture;

import com.szcgc.customer.annotation.Excel;
import com.szcgc.customer.model.base.FactorInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 销售收入
 *
 * @author chenjiaming
 * @date 2022-9-22 16:25:05
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "销售收入")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_cust_sales_revenue", schema = "gmis_customer")
public class SalesRevenue extends FactorInfo {

    @Excel(sample = "2022")
    @Schema(description = "统计年份", required = true)
    private String year;

    @Excel(sample = "188.88")
    @Schema(description = "一月金额(万元)")
    private BigDecimal one;

    @Excel(sample = "188.88")
    @Schema(description = "二月金额(万元)")
    private BigDecimal two;

    @Excel(sample = "188.88")
    @Schema(description = "三月金额(万元)")
    private BigDecimal three;

    @Excel(sample = "188.88")
    @Schema(description = "四月金额(万元)")
    private BigDecimal four;

    @Excel(sample = "188.88")
    @Schema(description = "五月金额(万元)")
    private BigDecimal five;

    @Excel(sample = "188.88")
    @Schema(description = "六月金额(万元)")
    private BigDecimal six;

    @Excel(sample = "188.88")
    @Schema(description = "七月金额(万元)")
    private BigDecimal seven;

    @Excel(sample = "188.88")
    @Schema(description = "八月金额(万元)")
    private BigDecimal eight;

    @Excel(sample = "188.88")
    @Schema(description = "九月金额(万元)")
    private BigDecimal night;

    @Excel(sample = "188.88")
    @Schema(description = "十月金额(万元)")
    private BigDecimal ten;

    @Excel(sample = "188.88")
    @Schema(description = "十一月金额(万元)")
    private BigDecimal eleven;

    @Excel(sample = "188.88")
    @Schema(description = "十二月金额(万元)")
    private BigDecimal twelve;

}
