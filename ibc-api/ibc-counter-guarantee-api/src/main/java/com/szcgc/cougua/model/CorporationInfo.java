package com.szcgc.cougua.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 企业保证
 *
 * @Author liaohong
 * @create 2022/9/19 14:57
 */
@Data
@Entity
@Table(name = "corporation", schema = "gmis_cusgua")
public class CorporationInfo extends CounterGuaranteeInfo {

    @Schema(description = "客户名称")
    @Column(length = 50)
    private String name;

    @Schema(description = "统一社会信用代码")
    @Column(length = 50, updatable = false)
    private String idNo;

    @Schema(description = "法定代表人")
    private String legal;

    @Schema(description = "联系人")
    private String contacts;

    @Schema(description = "联系电话")
    private String phone;

    @Schema(description = "法定代表人证件")
    private String legalCertificate;

    @Schema(description = "法定代表人国籍,数据字典类型:nationality")
    private String legalNationality;

    @Override
    public String cgType() {
        return CounterGuaranteeInfo.CORP;
    }
}
