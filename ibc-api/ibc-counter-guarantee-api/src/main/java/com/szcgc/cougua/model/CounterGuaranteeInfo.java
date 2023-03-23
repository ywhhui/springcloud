package com.szcgc.cougua.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Author liaohong
 * @create 2022/9/19 14:50
 */
@Data
@MappedSuperclass
public abstract class CounterGuaranteeInfo {

    public static final String INDI = "indi";   //个人保证
    public static final String CORP = "corp";   //企业保证
    public static final String MATE = "mate";   //实物保证

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;

    @Schema(name = "客户Id")
    @Column(updatable = false)
    protected int customerId;

    @Schema(name = "项目Id")
    @Column(updatable = false)
    protected int projectId;

    @Schema(description = "备注")
    @Column(length = 500)
    private String remarks;

    @Schema(name = "创建人")
    @Column(updatable = false)
    protected int createBy;

    @Schema(name = "创建时间")
    @Column(updatable = false)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)		// 反序列化
    @JsonSerialize(using = LocalDateTimeSerializer.class)		// 序列化
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime createAt;

    @Schema(name = "最后更新人")
    protected int updateBy;

    @Schema(name = "最后更新时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)		// 反序列化
    @JsonSerialize(using = LocalDateTimeSerializer.class)		// 序列化
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime updateAt;

    public abstract String cgType();

}
