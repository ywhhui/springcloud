package com.szcgc.customer.model;

import com.szcgc.customer.annotation.Excel;
import com.szcgc.customer.constant.DicTypeEnum;
import com.szcgc.customer.model.base.FactorInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 股权结构
 *
 * @author chenjiaming
 * @date 2022-9-29 18:01:14
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "股权结构")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_cust_equity_structure", schema = "gmis_customer")
public class EquityStructure extends FactorInfo {

    @Excel(sample = "***有限公司")
    @Schema(description = "股东名称")
    private String name;

    @Excel(sample = "个人", comboxType = DicTypeEnum.SHAREHOLDER_TYPE)
    @Schema(description = "股东类型")
    private Integer type;

    @Excel(sample = "货币")
    @Schema(description = "出资方式")
    @Column(columnDefinition = "text")
    private String paymet;

    @Excel(sample = "90%")
    @Schema(description = "出资比例")
    private String percent;

    @Schema(description = "简称")
    private String alias;

    @Excel(sample = "1000万美元")
    @Schema(description = "认缴金额")
    private String capital;

    @Excel(sample = "1000万元")
    @Schema(description = "实缴金额")
    private String capitalActl;

    @Schema(description = "出资时间")
    private String time;

    @Excel(sample = "***")
    @Schema(description = "备注")
    @Column(length = 500)
    private String remarks;

}
