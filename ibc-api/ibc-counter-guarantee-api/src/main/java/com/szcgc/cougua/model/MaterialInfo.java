package com.szcgc.cougua.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
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
 * 反担保措施
 * @Author liaohong
 * @create 2022/9/19 15:02
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "material", schema = "gmis_cusgua")
public class MaterialInfo extends CounterGuaranteeInfo {

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

    @Schema(description = "评估状态")
    @Column(insertable = false,columnDefinition = "int default 0")
    private int assessedStatus;

    @Schema(description = "评估渠道")
    @Column(length = 100)
    private String assessedSource;

    @Schema(description = "担保额")
    private BigDecimal guaranteeLimit;

    @Schema(description = "评估日期")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)		// 反序列化
    @JsonSerialize(using = LocalDateTimeSerializer.class)		// 序列化
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime assessedDate;

    @Override
    public String cgType() {
        return CounterGuaranteeInfo.MATE;
    }

}
