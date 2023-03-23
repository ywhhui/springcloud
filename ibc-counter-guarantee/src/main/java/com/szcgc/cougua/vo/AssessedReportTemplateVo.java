package com.szcgc.cougua.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.szcgc.cougua.constant.CounterGuaranteeTypeCateEnum;
import com.szcgc.cougua.constant.CounterGuaranteeTypePropEnum;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;

/**
 * 系统自动生成的评估报告
 * @Author liaohong
 * @create 2020/9/24 14:58
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssessedReportTemplateVo {

    private CounterGuaranteeTypeCateEnum cate;

    private int customerId;

    /**
     * 申请担保单位
     */
    private String customerName;

    private int projectId;

    private String remarks;

    private long originalValue;

    private int assessedAccountId;

    /**
     * 资产评估人员
     */
    private String assessedAccountName;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)		// 反序列化
    @JsonSerialize(using = LocalDateTimeSerializer.class)		// 序列化
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime assessedDate;

    private List<EnumMap<CounterGuaranteeTypePropEnum, String>> props;

}
