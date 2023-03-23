package com.szcgc.customer.model.manufacture;

import com.szcgc.customer.annotation.Excel;
import com.szcgc.customer.model.base.FactorInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 产品结构
 *
 * @author chenjiaming
 * @date 2022-9-21 09:45:14
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "产品结构")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_cust_product_structure", schema = "gmis_customer")
public class ProductStructure extends FactorInfo {

    @Excel(sample = "2022")
    @Schema(description = "统计年份", required = true)
    private String year;

    @Excel(sample = "05")
    @Schema(description = "统计月份", required = true)
    private String month;

    @Excel(sample = "***")
    @Schema(description = "主要产品名称", required = true)
    private String productName;

    @Excel(sample = "200")
    @Schema(description = "销售收入(万元)", required = true)
    private BigDecimal salesRevenue;

    @Excel(sample = "10.00")
    @Schema(description = "占总销售额比例(%)", required = true)
    private Double salesTotalRatio;

    @Excel(sample = "15.00")
    @Schema(description = "外销占比(%)", required = true)
    private Double exportSalesRatio;

    @Excel(sample = "5.00")
    @Schema(description = "内销占比(%)", required = true)
    private Double homeSalesRatio;

}
