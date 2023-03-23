package com.szcgc.project.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Author liaohong
 * @create 2022/10/11 19:09
 * 项目B角意见
 */
@Data
@Entity
@Table(name = "commentinfo", schema = "gmis_project")
public class CommentInfo {

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "项目Id")
    @Column(updatable = false)
    private int projectId;

    @Schema(description = "B角Id")
    @Column(updatable = false)
    private int accountId;

    @Schema(description = "B角名")
    @Column(length = 20,updatable = false)
    private String accountName;

    @Schema(description = "B角意见")
    @Column(length = 50, updatable = false)
    private String opinion;

    @Schema(description = "B角意见详情")
    @Column(updatable = false, length = 200)
    private String remarks;

    @Schema(description = "B角报告文件")
    @Column(updatable = false)
    private int fileId;

    @Column(updatable = false)
    private LocalDateTime createAt;

    //其实也可以考虑把这两个字段直接冗余处理
    @Transient
    @Schema(description = "B角报告文件地址")
    private String filePath;

}
