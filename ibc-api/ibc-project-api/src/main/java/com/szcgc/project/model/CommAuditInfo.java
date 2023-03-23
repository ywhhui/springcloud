package com.szcgc.project.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Author liaohong
 * @create 2021/2/7 15:09
 * 通用审批意见表
 */
@Data
@Entity
@Table(name = "commauditinfo", schema = "gmis_project")
public class CommAuditInfo {

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "项目Id")
    @Column(updatable = false)
    private int projectId;

    @Schema(description = "审批人Id")
    @Column(updatable = false)
    private int accountId;

    @Schema(description = "审批人名")
    @Column(length = 20, updatable = false)
    private String accountName;

    @Schema(description = "审批人意见")
    @Column(length = 50, updatable = false)
    private String opinion;

    @Schema(description = "审批人意见详情")
    @Column(updatable = false, length = 200)
    private String remarks;

    @Schema(description = "审批节点名")
    @Column(length = 20, updatable = false)
    private String caseName;

    @Schema(description = "审批节点名")
    @Column(length = 20, updatable = false)
    private String caseNameCn;

    @Column(updatable = false)
    private LocalDateTime createAt;

}
