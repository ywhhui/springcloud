package com.szcgc.project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 解保信息
 */
@Data
@Entity
@Table(name = "releaseinfo", schema = "gmis_project")
public class ReleaseInfo {

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Schema(name = "项目Id")
    @Column(updatable = false)
    private int projectId;

    @Schema(description = "还款日期")
    @Column(length = 50)
    private String repaymentDate;

    @Schema(description = "解保金额(元)")
    private BigDecimal releaseAmt;

    @Schema(description = "解保说明")
    @Column(length = 500)
    private String releaseDescribe;

    @Schema(description = "户名")
    @Column(length = 50)
    private String releaseAccountName;

    @Schema(description = "开户行编号")
    @Column(length = 50)
    private String openBankNo;

    @Schema(description = "开户行名称")
    @Column(length = 50)
    private String openBankName;

    @Schema(description = "退回银行账号")
    @Column(length = 50)
    private String returnBankAccount;

    @Schema(description = "意见")
    @Column(length = 500)
    private String confirmOpinion;

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

    @Schema(description = "审核用户Id")
    private int auditBy;

    @Schema(description = "审核时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)		// 反序列化
    @JsonSerialize(using = LocalDateTimeSerializer.class)		// 序列化
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime auditAt;

    @Schema(description = "审核意见(1:同意,2:不同意)")
    private int auditRst;

    @Schema(description = "审核意见")
    @Column(length = 500)
    private String auditOpinion;

}
