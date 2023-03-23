package com.szcgc.customer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.szcgc.project.constant.BusinessTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 管户信息
 *
 * @author chenjiaming
 * @date 2022-10-10 14:24:27
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "管户信息")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_cust_custodian", schema = "gmis_customer")
public class Custodian {

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(description = "客户id", required = true)
    private Integer customerId;

    @Schema(description = "客户名称")
    private String customerName;

    @Schema(description = "管护人id", required = true)
    private Integer custodianId;

    @Schema(description = "管护人名称")
    private String custodianName;

    @Schema(description = "业务类型")
    private BusinessTypeEnum busiType;

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
