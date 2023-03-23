package com.szcgc.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author liaohong
 * @create 2022/10/13 9:07
 */
@Data
public class BaseInVo {

    @Schema(description = "项目Id")
    protected int projectId;

    @Schema(description = "任务Id")
    protected String taskId;
}
