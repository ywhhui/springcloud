package com.szcgc.cougua.model;

import com.szcgc.cougua.jpa.CutGuaCryptAttributeConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;

/**
 * 个人保证
 * @Author liaohong
 * @create 2022/9/19 14:43
 */
@Data
@Entity
@Table(name = "individual", schema = "gmis_cusgua")
public class IndividualInfo extends CounterGuaranteeInfo {

    @Schema(description = "客户名称")
    @Column(length = 50)
    private String name;

    @Schema(description = "证件类型")
    @Column(length = 50)
    private String certificate;

    @Schema(description = "证件号码")
    @Column(length = 50)
    @Convert(converter = CutGuaCryptAttributeConverter.class)
    private String identifyNo;

    @Schema(description = "联系电话")
    @Column(length = 50)
    private String phone;

    @Schema(description = "联系地址")
    @Column(length = 50)
    private String address;

    @Schema(description = "是否夫妻保证 0不是 1是")
    private byte coupled;

    @Schema(description = "配偶姓名")
    @Column(length = 50)
    private String coupleName;

    @Schema(description = "配偶证件类型")
    @Column(length = 50)
    private String coupleCertificate;

    @Schema(description = "配偶证件号码")
    @Column(length = 50)
    @Convert(converter = CutGuaCryptAttributeConverter.class)
    private String coupleIdentifyNo;

    @Schema(description = "配偶联系电话")
    @Column(length = 50)
    private String couplePhone;

    @Schema(description = "配偶联系地址")
    @Column(length = 50)
    private String coupleAddress;

    @Override
    public String cgType() {
        return CounterGuaranteeInfo.INDI;
    }
}
