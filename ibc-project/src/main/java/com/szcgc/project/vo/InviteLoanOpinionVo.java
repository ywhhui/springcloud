package com.szcgc.project.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.szcgc.project.constant.ProjectActEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 请款审批意见列表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InviteLoanOpinionVo {

    @Schema(description = "操作类型(1:通过,2:退回)")
    private String auditRst;

    @Schema(description = "意见详情")
    private String confirmOpinion;

    /**
     * 节点
     */
    private ProjectActEnum projectActEnum;

    @Schema(name = "操作人")
    private int createBy;

    @Schema(name = "操作人名称")
    private String createByName;

    @Schema(name = "结束时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)		// 反序列化
    @JsonSerialize(using = LocalDateTimeSerializer.class)		// 序列化
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createAt;

}
