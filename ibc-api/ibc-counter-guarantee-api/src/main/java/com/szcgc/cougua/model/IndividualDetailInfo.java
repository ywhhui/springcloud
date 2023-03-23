package com.szcgc.cougua.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.szcgc.cougua.constant.CounterGuaranteeOneCateEnum;
import com.szcgc.cougua.constant.CounterGuaranteeTwoCateEnum;
import com.szcgc.cougua.constant.CounterGuaranteeTypeCateEnum;
import com.szcgc.cougua.constant.CounterGuaranteeTypePropEnum;
import com.szcgc.cougua.jpa.CutGuaMaterialPropAttributeConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.EnumMap;

/**
 * 个人保证详情
 * @Author liaohong
 * @create 2022/9/19 14:43
 */
@Data
@Entity
@Table(name = "individual_detail", schema = "gmis_cusgua")
public class IndividualDetailInfo {

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Schema(description = "个人保证id")
    @Column(updatable = false)
    private int individualId;

    @Schema(name = "项目Id")
    @Column(updatable = false)
    private int projectId;

    @Schema(description = "附件")
    @Column(length = 2000,columnDefinition = "text")
    private String fileList;

    @Schema(description = "担保一级类型")
    @Enumerated(EnumType.STRING)
    private CounterGuaranteeOneCateEnum oneCate;

    @Schema(description = "担保二级类型")
    @Enumerated(EnumType.STRING)
    private CounterGuaranteeTwoCateEnum twoCate;

    @Schema(description = "担保类型")
    @Enumerated(EnumType.STRING)
    private CounterGuaranteeTypeCateEnum cate;

    @Schema(description = "担保类型对应属性值")
    @Column(length = 2000,columnDefinition = "text")
    @Convert(converter = CutGuaMaterialPropAttributeConverter.class)
    private EnumMap<CounterGuaranteeTypePropEnum, String> props;

    @Schema(description = "原值")
    private BigDecimal originalValue;

    @Schema(description = "评估值")
    private BigDecimal assessedValue;

    @Schema(description = "可担保额(元)")
    private BigDecimal guaranteeLimit;

    @Schema(description = "备注")
    @Column(length = 500)
    private String remarks;

    @Schema(name = "创建人")
    @Column(updatable = false)
    private int createBy;

    @Schema(name = "创建时间")
    @Column(updatable = false)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)		// 反序列化
    @JsonSerialize(using = LocalDateTimeSerializer.class)		// 序列化
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createAt;

    @Schema(name = "最后更新人")
    private int updateBy;

    @Schema(name = "最后更新时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)		// 反序列化
    @JsonSerialize(using = LocalDateTimeSerializer.class)		// 序列化
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateAt;

}
