package com.szcgc.project.vo.meet;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * @Author liaohong
 * @create 2022/10/21 17:06
 */
public class MeetingDftVo {

    @Schema(description = "主持人Id")
    public int hostId;

    @Schema(description = "评委Id列表")
    public List<Integer> judges;

    @Schema(description = "记录员Id")
    public int recorderId;
}
