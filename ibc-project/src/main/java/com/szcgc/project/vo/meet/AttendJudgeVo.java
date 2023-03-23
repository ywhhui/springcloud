package com.szcgc.project.vo.meet;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @Author: chenxinli
 * @Date: 2020/10/13 14:39
 * @Description:
 */
@Data
public class AttendJudgeVo {

    @Schema(description = "排会申请Id")
    private int attendId;

    @Schema(description = "项目Id")
    private int projectId;

    @Schema(description = "主持人Id")
    private int hostId;

    @Schema(description = "评委Id列表")
    private List<Integer> judgeIds;
}
