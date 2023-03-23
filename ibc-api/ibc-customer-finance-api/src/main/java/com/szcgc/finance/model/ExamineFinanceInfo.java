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
 * 登记保后检查信息-新增财务分析
 * @Author liaohong
 * @create 2020/11/18 16:28
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "examinefinanceinfo", schema = "gmis_customer")
public class ExamineFinanceInfo {

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "项目Id")
    @Column(updatable = false)
    private int projectId;

    @Schema(description = "客户Id", required = true)
    @Column(updatable = false)
    protected Integer customerId;

    @Schema(description = "财务报表")
    @Column(length = 10)
    private String financialReport;

    @Schema(description = "货币资金")
    @Column(length = 10)
    private String cce;

    @Schema(description = "应收")
    @Column(length = 20)
    private String ar;

    @Schema(description = "存货")
    @Column(length = 10)
    private String fd;

    @Schema(description = "待摊费用")
    @Column(length = 20)
    private String de;

    @Schema(description = "长期投资")
    @Column(length = 10)
    private String lti;

    @Schema(description = "固定资产")
    @Column(length = 10)
    private String fa;

    @Schema(description = "总资产")
    @Column(length = 20)
    private String ta;

    @Schema(description = "借款lsl")
    @Column(length = 10)
    private String lsl;

    @Schema(description = "应付")
    @Column(length = 20)
    private String pay;

    @Schema(description = "应交税金")
    @Column(length = 10)
    private String at;

    @Schema(description = "实收资本")
    @Column(length = 10)
    private String cuc;

    @Schema(description = "资本公积")
    @Column(length = 20)
    private String pis;

    @Schema(description = "留存收益")
    @Column(length = 10)
    private String re;

    @Schema(description = "负债及权益")
    @Column(length = 20)
    private String lae;

    @Schema(description = "销售收入")
    @Column(length = 10)
    private String oi;

    @Schema(description = "成本税金")
    @Column(length = 10)
    private String cat;

    @Schema(description = "期间费用")
    @Column(length = 20)
    private String pe;

    @Schema(description = "投资收益")
    @Column(length = 10)
    private String ifi;

    @Schema(description = "所得税")
    @Column(length = 20)
    private String it;

    @Schema(description = "净利润")
    @Column(length = 10)
    private String nep;

    @Schema(description = "创建人")
    @Column(updatable = false)
    protected Integer createBy;

    @Schema(description = "创建时间")
    @Column(updatable = false)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createAt;

    @Schema(description = "最后更新人")
    protected Integer updateBy;

    @Schema(description = "最后更新时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)		// 反序列化
    @JsonSerialize(using = LocalDateTimeSerializer.class)		// 序列化
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @Column(name = "update_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    protected LocalDateTime updateAt;

    @PrePersist
    public void onCreate() {
        createAt = LocalDateTime.now();
    }
}
