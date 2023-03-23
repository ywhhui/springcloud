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
import java.time.LocalDateTime;

/**
 * 待解保的抵押物信息
 */
@Data
@Entity
@Table(name = "releasedetailinfo", schema = "gmis_project")
public class ReleaseDetailInfo {

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Schema(name = "项目Id")
    @Column(updatable = false)
    private int projectId;

    @Schema(description = "待解保的抵押物类型 1：反担保措施 2:保证金： 3：保理业务品种的应收账款")
    private String releaseType;

    @Schema(description = "待解保的抵押物id")
    @Column(length = 2000,columnDefinition = "text")
    private String releaseIds;

    @Column
    @Schema(description = "是否解除抵押物，0否，1是")
    private int releaseStatus;

    @Schema(description = "解除抵押物的日期")
    @Column(updatable = false)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)		// 反序列化
    @JsonSerialize(using = LocalDateTimeSerializer.class)		// 序列化
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime releaseDate;

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
