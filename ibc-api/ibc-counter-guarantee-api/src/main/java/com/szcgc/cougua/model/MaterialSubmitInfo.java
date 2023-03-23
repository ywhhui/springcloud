package com.szcgc.cougua.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
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
 * 分配评估师的提交
 * @Author liaohong
 * @create 2022/9/19 15:02
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "materialsubmitinfo", schema = "gmis_cusgua",uniqueConstraints = {@UniqueConstraint(name = "unuque_projectId", columnNames = {"projectId"})})
public class MaterialSubmitInfo {

        @Id
        @Column(name = "id", length = 11)
        @GeneratedValue(strategy = GenerationType.AUTO)
        private int id;

        @Schema(name = "项目Id")
        @Column(updatable = false)
        private int projectId;

        @Schema(description = "被分配评估师")
        private int assessingAccountId;

        @Schema(description = "备注")
        @Column(length = 500)
        private String remarks;

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

        @Schema(name = "最后更新人")
        private int updateBy;

        @Schema(name = "最后更新时间")
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)		// 反序列化
        @JsonSerialize(using = LocalDateTimeSerializer.class)		// 序列化
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime updateAt;

}
