package com.szcgc.finance.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 保后检查登记信息
 * @Author liaohong
 * @create 2020/11/18 16:28
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "examineinfo", schema = "gmis_customer")
public class ExamineInfo {

    public static final int STATUS_INIT = 0;
    public static final int STATUS_ENROLLED = 1;
    public static final int STATUS_AUDITED = 2;

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "项目Id")
    @Column(updatable = false)
    private int projectId;

    @Schema(description = "保后检查日期")
    private String examineDate;

    @Schema(description = "财务报表")
    @Column(length = 10)
    private String financialReport;

    @Schema(description = "控制人")
    @Column(length = 10)
    private String ctrlName;

    @Schema(description = "控制人联系电话")
    @Column(length = 20)
    private String ctrlPhone;

    @Schema(description = "财务负责人")
    @Column(length = 10)
    private String cfoName;

    @Schema(description = "财务负责人联系电话")
    @Column(length = 20)
    private String cfoPhone;

    @Schema(description = "风险预警等级")
    @Column(length = 10)
    private Integer riskLevel;

    @Schema(description = "员工人数")
    private int employCount;

    @Schema(description = "工资是否按时支付,1:是,0:否")
    private int salaryPayable;

    @Schema(description = "工资支付截止日")
    private String salaryPayDate;

    @Schema(description = "房租是否按时支付,1:是,0:否")
    private int rentPayable;

    @Schema(description = "房租支付截止日")
    private String rentPayDate;

    @Schema(description = "水电费是否按时支付,1:是,0:否")
    private int hydroPayable;

    @Schema(description = "水电费支付截止日")
    private String hydroPayDate;

    @Schema(description = "企业贷款卡是否有不良记录,1:是,0:否")
    private int hasBadDebt;

    @Schema(description = "企业、实际控制人、抵押物所有人是否有新增诉讼,1:是,0:否")
    private int hasNewLawsuit;

    @Schema(description = "反担保措施冻结、查封等情况是否查询,1:是,0:否")
    private int hasCheckCg;

    @Schema(description = "保后检查情况说明")
    @Column(length = 200)
    private String remarks;

    @Schema(description = "登记人")
    @Column(updatable = false)
    private int enrollAccountId;

    @Schema(description = "创建时间")
    @Column(updatable = false)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createAt;

    @Schema(description = "状态,0:初始化,1:已提交2:已审核")
    private int ibcStatus;

    @Schema(description = "保后检查审核意见")
    @Column(length = 200)
    private String auditComment;

    @Schema(description = "审核人")
    private int auditAccountId;

    @Schema(description = "审核时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime auditAt;

    @PrePersist
    public void onCreate() {
        createAt = LocalDateTime.now();
    }
}
