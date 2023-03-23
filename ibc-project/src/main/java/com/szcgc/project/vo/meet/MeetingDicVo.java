package com.szcgc.project.vo.meet;

import com.szcgc.comm.model.IbcTree;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * @Author liaohong
 * @create 2022/10/13 11:04
 */
public class MeetingDicVo {

    @Schema(description = "主持人列表")
    public List<IbcTree> hosts;

    @Schema(description = "评委列表")
    public List<IbcTree> judges;

    @Schema(description = "记录员列表")
    public List<IbcTree> recorders;
}
