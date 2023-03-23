package com.szcgc.customer.model.manufacture;

import com.szcgc.customer.annotation.Excel;
import com.szcgc.customer.model.base.FactorInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 营销模式
 *
 * @author chenjiaming
 * @date 2022-9-22 15:54:34
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "营销模式")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_cust_marketing_pattern", schema = "gmis_customer")
public class MarketingPattern extends FactorInfo {

    @Excel(sample = "2022")
    @Schema(description = "统计年份", required = true)
    private String year;

    @Excel(sample = "05")
    @Schema(description = "统计月份", required = true)
    private String month;

    @Excel(sample = "***")
    @Schema(description = "客户名称", required = true)
    private String custName;

    @Excel(sample = "923***Q0R")
    @Schema(description = "统一社会信用代码", required = true)
    private String uniqueNo;

    @Excel(sample = "3000")
    @Schema(description = "销售额(万元)")
    private BigDecimal sale;

    @Excel(sample = "20.00")
    @Schema(description = "销售额占比", required = true)
    private Double saleRatio;

    @Excel(sample = "3000")
    @Schema(description = "结算周期")
    private String settlementInterval;

    @Excel(sample = "***")
    @Schema(description = "供应产品")
    private String supplyProducts;

    @Excel(sample = "***")
    @Schema(description = "备注")
    @Column(name = "remark", columnDefinition = "text")
    private String remarks;

}
