package com.szcgc.project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.szcgc.project.constant.ProjectActEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 请款信息
 */
@Data
@Entity
@Table(name = "inviteloaninfo", schema = "gmis_project")
public class InviteLoanInfo {

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Schema(name = "项目Id")
    @Column(updatable = false)
    private int projectId;

    @Schema(description = "流程节点名称")
    @Enumerated(EnumType.STRING)
    private ProjectActEnum projectActEnum;

    @Schema(description = "收款开户行")
    @Column(length = 50)
    private String inviteBankName;

    @Schema(description = "收款账号")
    @Column(length = 50)
    private String inviteBankAccount;

    @Schema(description = "备注")
    @Column(length = 500)
    private String remark;

    @Schema(description = "操作类型(1:通过,2:退回)")
    private String auditRst;

    @Schema(description = "意见详情")
    @Column(length = 500)
    private String confirmOpinion;

    @Schema(name = "操作人")
    @Column(updatable = false)
    private int createBy;

    @Schema(name = "操作时间")
    @Column(updatable = false)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)		// 反序列化
    @JsonSerialize(using = LocalDateTimeSerializer.class)		// 序列化
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createAt;

}
