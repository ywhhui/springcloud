package com.szcgc.project.vo;

import com.szcgc.project.model.ProjectSupervisorInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author liaohong
 * @create 2020/9/25 16:46
 */
@Data
public class ProjectSupervisorVo extends ProjectVo {

    @Schema(description = "项目责任A角")
    public String roleA;

    @Schema(description = "项目责任B角")
    public String roleB;

    @Schema(description = "项目处理A角")
    public String dealA;

    @Schema(description = "项目处理B角")
    public String dealB;

    @Schema(description = "A角分担比例")
    public int percentA;

    @Schema(description = "B角分担比例")
    public int percentB;

    @Schema(description = "部门Id")
    public int departId;

    @Schema(description = "部门")
    public String departName;

    public void copySupervisor(ProjectSupervisorInfo supervisor) {
        this.roleA = supervisor.getRoleA();
        this.roleB = supervisor.getRoleB();
        this.dealA = supervisor.getDealA();
        this.dealB = supervisor.getDealB();
        this.percentA = supervisor.getPercentA();
        this.percentB = supervisor.getPercentB();
        this.departId = supervisor.getDepartmentId();
        this.departName = supervisor.getDepartmentName();
    }

}
