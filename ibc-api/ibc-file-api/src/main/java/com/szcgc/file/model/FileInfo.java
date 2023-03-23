package com.szcgc.file.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.szcgc.file.constant.FileCateEnum;
import com.szcgc.file.jpa.PathAttributeConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Author liaohong
 * @create 2022/9/26 9:16
 */
@Data
@Entity
@Table(name = "fileinfo", schema = "gmis_file")
public class FileInfo {

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "项目Id")
    @Column(updatable = false)
    private int projectId;

    @Schema(description = "文件名")
    @Column(length = 50)
    private String name;

    @Schema(description = "材料类型")
    @Column(length = 20, updatable = false)
    @Enumerated(EnumType.STRING)
    private FileCateEnum cate;

    @Column(length = 20, updatable = false)
    private String cateName;    //为数据导入做预留

    @Schema(description = "文件地址")
    @Column(length = 300, updatable = false)
    @Convert(converter = PathAttributeConvert.class)
    private String path;

    @Schema(description = "备注")
    @Column(length = 100)
    private String remarks;

    @Schema(description = "删除状态")
    @Column(length = 100)
    private byte delTag;     //目前是物理删除，预留成逻辑删除，

    @Column(updatable = false)
    private int createBy;

    @Column(updatable = false)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)        // 反序列化
    @JsonSerialize(using = LocalDateTimeSerializer.class)        // 序列化
    private LocalDateTime createAt;

    private int updateBy;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)        // 反序列化
    @JsonSerialize(using = LocalDateTimeSerializer.class)        // 序列化
    private LocalDateTime updateAt;

}
