package com.szcgc.customer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 天眼查企业
 *
 * @author chenjiaming
 * @date 2022-10-10 11:44:43
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "天眼查企业信息")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_cust_company_tyc", schema = "gmis_customer")
public class TycCompany  {

    @Id
    @Schema(description = "社会信用代码")
    @Column(length = 50, updatable = false)
    private String idNo;

    @Schema(description = "联系人")
    private String contacts;

    @Schema(description = "联系电话")
    private String phone;

    @Schema(description = "法定代表人")
    @Column(length = 10)
    private String legal;

    @Schema(description = "法定代表人证件")
    private String legalIdNo;

    @Schema(description = "企业评分,万分制")
    private Integer percentileScore;

    @Schema(description = "人员规模")
    private String staffNumRange;

    @Schema(description = "经营开始时间,时间戳")
    private Long fromTime;

    @Schema(description = "法人类型,1 人 2 公司")
    private Integer type;

    @Schema(description = "股票名")
    private String bondName;

    @Schema(description = "天眼查企业id")
    private Long tid;

    @Schema(description = "是否是小微企业 0不是 1是")
    private Integer isMicroEnt;

    @Schema(description = "股票曾用名")
    private String usedBondName;

    @Schema(description = "注册号")
    private String regNumber;

    @Schema(description = "注册资本")
    private String regCapital;

    @Schema(description = "企业名")
    private String name;

    @Schema(description = "登记机关")
    private String regInstitute;

    @Schema(description = "注册地址")
    private String regLocation;

    @Schema(description = "行业")
    private String industry;

    @Schema(description = "核准时间,时间戳")
    private Long approvedTime;

    @Schema(description = "更新时间,时间戳")
    private Long updateTimes;

    @Schema(description = "参保人数")
    private Integer socialStaffNum;

    @Schema(description = "企业标签")
    private String tags;

    @Schema(description = "纳税人识别号")
    private String taxNumber;

    @Schema(description = "经营范围")
    @Column(columnDefinition = "text")
    private String businessScope;

    @Schema(description = "英文名")
    private String property3;

    @Schema(description = "简称")
    private String alias;

    @Schema(description = "组织机构代码")
    private String orgNumber;

    @Schema(description = "企业状态")
    private String regStatus;

    @Schema(description = "成立日期,时间戳")
    private Long estiblishTime;

    @Schema(description = "股票类型")
    private String bondType;
    @Schema(description = "经营结束时间,时间戳")
    private Long toTime;

    @Schema(description = "实收注册资金")

    private String actualCapital;

    @Schema(description = "企业类型")
    private String companyOrgType;

    @Schema(description = "省份简称")
    private String base;

    @Schema(description = "曾用名")
    private String historyNames;

    @Schema(description = "曾用名")
    private String historyNameList;

    @Schema(description = "股票号")
    private String bondNum;

    @Schema(description = "注册资本币种 人民币 美元 欧元 等")
    private String regCapitalCurrency;

    @Schema(description = "实收注册资本币种 人民币 美元 欧元 等")
    private String actualCapitalCurrency;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "网址")
    @Column(columnDefinition = "text")
    private String websiteList;

    @Schema(description = "吊销日期,时间戳")
    private Long revokeDate;

    @Schema(description = "吊销原因")
    @Column(columnDefinition = "text")
    private String revokeReason;

    @Schema(description = "注销日期,时间戳")
    private Long cancelDate;

    @Schema(description = "注销原因")
    @Column(columnDefinition = "text")
    private String cancelReason;

    @Schema(description = "市")
    private String city;

    @Schema(description = "区")
    private String district;

    @Schema(description = "国民经济行业分类")
    private String industryAll;

    @Schema(description = "备注")
    @Column(length = 500)
    private String remarks;

    @Schema(description = "创建人")
    @Column(updatable = false)
    private Integer createBy;

    @Schema(description = "创建时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)		// 反序列化
    @JsonSerialize(using = LocalDateTimeSerializer.class)		// 序列化
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createAt;

    @Schema(description = "最后更新人")
    private Integer updateBy;

    @Schema(description = "最后更新时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)		// 反序列化
    @JsonSerialize(using = LocalDateTimeSerializer.class)		// 序列化
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateAt;
}
