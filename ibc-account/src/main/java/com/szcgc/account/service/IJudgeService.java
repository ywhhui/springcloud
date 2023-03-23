package com.szcgc.account.service;

import com.szcgc.account.model.system.JudgeInfo;
import com.szcgc.comm.IbcService;

import java.util.List;

/**
 * @Author: chenxinli
 * @Date: 2020/7/29 18:02
 * @Description:
 */
public interface IJudgeService extends IbcService<JudgeInfo, Integer> {

    List<JudgeInfo> getJudgeInfosByMeetingType(String meetingType);
}
