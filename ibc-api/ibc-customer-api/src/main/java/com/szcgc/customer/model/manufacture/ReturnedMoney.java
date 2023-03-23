package com.szcgc.customer.model.manufacture;

import com.szcgc.customer.annotation.Excel;
import com.szcgc.customer.model.base.FactorInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 回款核实
 *
 * @author chenjiaming
 * @date 2022-9-22 17:09:35
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "回款核实")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_cust_returned_money", schema = "gmis_customer")
public class ReturnedMoney extends FactorInfo {

    @Excel(sample = "2022")
    @Schema(description = "统计年份", required = true)
    private String year;

    @Excel(sample = "62***48")
    @Schema(description = "账户名称", required = true)
    private String accountName;

    @Excel(sample = "中国**银行")
    @Schema(description = "结算银行", required = true)
    private String settlementBank;

    @Excel(sample = "188.88")
    @Schema(description = "一月金额(万元)", required = true)
    private BigDecimal one;

    @Excel(sample = "188.88")
    @Schema(description = "二月金额(万元)", required = true)
    private BigDecimal two;

    @Excel(sample = "188.88")
    @Schema(description = "三月金额(万元)", required = true)
    private BigDecimal three;

    @Excel(sample = "188.88")
    @Schema(description = "四月金额(万元)", required = true)
    private BigDecimal four;

    @Excel(sample = "188.88")
    @Schema(description = "五月金额(万元)", required = true)
    private BigDecimal five;

    @Excel(sample = "188.88")
    @Schema(description = "六月金额(万元)", required = true)
    private BigDecimal six;

    @Excel(sample = "188.88")
    @Schema(description = "七月金额(万元)", required = true)
    private BigDecimal seven;

    @Excel(sample = "188.88")
    @Schema(description = "八月金额(万元)", required = true)
    private BigDecimal eight;

    @Excel(sample = "188.88")
    @Schema(description = "九月金额(万元)", required = true)
    private BigDecimal night;

    @Excel(sample = "188.88")
    @Schema(description = "十月金额(万元)", required = true)
    private BigDecimal ten;

    @Excel(sample = "188.88")
    @Schema(description = "十一月金额(万元)", required = true)
    private BigDecimal eleven;

    @Excel(sample = "188.88")
    @Schema(description = "十二月金额(万元)", required = true)
    private BigDecimal twelve;

}
