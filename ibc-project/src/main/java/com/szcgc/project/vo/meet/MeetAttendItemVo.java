package com.szcgc.project.vo.meet;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author liaohong
 * @create 2022/10/21 15:31
 */
@Data
public class MeetAttendItemVo {

    @Schema(description = "排会申请Id")
    private int attendId;

    @Schema(description = "项目Id")
    private int projectId;

    @Schema(description = "任务Id")
    private String taskId;
}
