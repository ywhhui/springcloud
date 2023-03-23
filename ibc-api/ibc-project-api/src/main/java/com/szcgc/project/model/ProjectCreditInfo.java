package com.szcgc.project.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Author liaohong
 * @create 2021/2/7 15:32
 * projectInfo的补充信息，记录某个额度项下项目占用了哪笔额度
 */
@Entity
@Table(name = "pcreditinfo", schema = "gmis_project")
public class ProjectCreditInfo {

    public static final int PHASE_INIT = 1;
    public static final int PHASE_PROPOSAL = 2;
    public static final int PHASE_EVALUATE = 3;

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "项目Id")
    @Column(updatable = false)
    private int projectId;

    @Schema(description = "额度Id")
    private int creditId;

    @Schema(description = "阶段")
    private int phaseValue;

    @Schema(description = "备注")
    @Column(length = 500)
    private String remarks;

    @Schema(description = "生成时间")
    @Column(updatable = false)
    private LocalDateTime createAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getCreditId() {
        return creditId;
    }

    public void setCreditId(int creditId) {
        this.creditId = creditId;
    }

    public int getPhaseValue() {
        return phaseValue;
    }

    public void setPhaseValue(int phaseValue) {
        this.phaseValue = phaseValue;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public void bak() {
        this.remarks += ";" + phaseValue + ":" + creditId;
    }

    public boolean isNormal() {
        return phaseValue == PHASE_EVALUATE;
    }
}
