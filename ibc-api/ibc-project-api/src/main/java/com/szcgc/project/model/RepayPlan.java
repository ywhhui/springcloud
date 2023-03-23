package com.szcgc.project.model;

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
 * 还款计划
 *
 * @author chenjiaming
 * @date 2022-10-21 17:39:21
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "还款计划")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_repay_plan", schema = "gmis_project")
public class RepayPlan {

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(description = "项目Id")
    @Column(updatable = false)
    private Integer projectId;

    @Schema(description = "客户id")
    @Column(updatable = false)
    private Integer customerId;

    @Schema(description = "放款id")
    @Column(updatable = false)
    private Integer loanId;

    @Schema(description = "期次")
    private Integer period;

    @Schema(description = "应还日期")
    private String shouldRepayDate;

    @Schema(description = "应还本金")
    private Long shouldRepayCapital;

    @Schema(description = "应还利息")
    private Long shouldRepayAccrual;

    @Schema(description = "实际还款日期")
    private String realRepayDate;

    @Schema(description = "本次实还本金")
    private Long realRepayCapital;

    @Schema(description = "本次实还利息")
    private Long realRepayAccrual;

    @Schema(description = "是否入账")
    private Boolean credited;

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
