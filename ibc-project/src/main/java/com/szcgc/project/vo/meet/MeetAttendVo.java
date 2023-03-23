package com.szcgc.project.vo.meet;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @Author liaohong
 * @create 2022/10/18 19:43
 */
@Data
public class MeetAttendVo {

    @Schema(description = "会议Id")
    private int meetingId;

    @Schema(description = "项目列表")
    private List<MeetAttendItemVo> items;
}
