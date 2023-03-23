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
 * 进货渠道
 *
 * @author chenjiaming
 * @date 2022-9-22 09:12:06
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "进货渠道")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_cust_inbound_channel", schema = "gmis_customer")
public class InboundChannel extends FactorInfo {

    @Excel(sample = "2022")
    @Schema(description = "统计年份", required = true)
    private String year;

    @Excel(sample = "05")
    @Schema(description = "统计月份", required = true)
    private String month;

    @Excel(sample = "***")
    @Schema(description = "供应商名称", required = true)
    private String supplierName;

    @Excel(sample = "企业", comboxType = DicTypeEnum.CUST_TYPE)
    @Schema(description = "供应商类型", required = true)
    private Integer supplierType;

    @Excel(sample = "923***Q0R")
    @Schema(description = "统一社会信用代码/身份证号", required = true)
    private String uniqueNo;

    @Excel(sample = "***")
    @Schema(description = "供应产品")
    private String supplyProducts;

    @Excel(sample = "3000")
    @Schema(description = "采购额(万元)")
    private BigDecimal purchaseAmount;

    @Excel(sample = "3000")
    @Schema(description = "结算周期")
    private String settlementInterval;

}
