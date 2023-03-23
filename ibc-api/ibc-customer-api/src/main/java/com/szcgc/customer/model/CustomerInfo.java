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
 * 客户信息
 *
 * @author chenjiaming
 * @date 2022-9-29 09:09:29
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "客户信息")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_cust_info", schema = "gmis_customer")
public class CustomerInfo {

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(description = "客户名称", required = true)
    private String name;

    @Schema(description = "客户类型", required = true)
    @Column(updatable = false)
    private Integer cate;

    @Schema(description = "统一社会信用代码/证件号", required = true)
    @Column(length = 50, updatable = false)
    private String idNo;

    @Schema(description = "法定代表人")
    private String legal;

    @Schema(description = "联系人")
    private String contacts;

    @Schema(description = "联系电话")
    private String phone;

    @Schema(description = "证件类型,数据字典类型:certificateType")
    private Integer certificateType;

    @Schema(description = "法定代表人证件")
    private String legalCertificate;

    @Schema(description = "法定代表人国籍,数据字典类型:nationality")
    private String legalNationality;

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
