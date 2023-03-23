package com.szcgc.project.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Author liaohong
 * @create 2020/9/15 16:26
 * 项目责任表
 */
@Data
@Entity
@Table(name = "psupervisorinfo", schema = "gmis_project")
public class ProjectSupervisorInfo {

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "项目Id")
    @Column(updatable = false)
    private int projectId;

    @Schema(description = "项目经理A所属部门")
    private int departmentId;

    @Schema(description = "项目经理A所属部门名")
    public String departmentName;

    @Schema(description = "项目A角Id")
    private int roleAId;

    @Schema(description = "项目A角")
    public String roleA;

    @Schema(description = "分担比例")
    private int percentA;    //百分比

    @Schema(description = "项目B角Id")
    private int roleBId;

    @Schema(description = "项目B角")
    public String roleB;

    @Schema(description = "分担比例")
    private int percentB;    //百分比

    @Schema(description = "项目C角Id")
    private int roleCId;

    @Schema(description = "项目C角")
    public String roleC;

    @Schema(description = "分担比例")
    private int percentC;    //百分比

    @Schema(description = "法务经理Id")
    private String lawyerId;

    @Schema(description = "法务经理")
    private String lawyer;

    @Schema(description = "项目处理A角Id")
    private int dealAId;

    @Schema(description = "项目处理A角")
    public String dealA;

    @Schema(description = "项目处理A角Id")
    private int dealBId;

    @Schema(description = "项目处理A角")
    public String dealB;

    @Column(updatable = false)
    private int createBy;

    @Column(updatable = false)
    private LocalDateTime createAt;

    private LocalDateTime updateAt;

}
