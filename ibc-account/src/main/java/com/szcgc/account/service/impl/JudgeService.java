package com.szcgc.account.service.impl;

import com.szcgc.account.model.system.JudgeInfo;
import com.szcgc.account.repository.JudgeRepository;
import com.szcgc.account.service.IJudgeService;
import com.szcgc.comm.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: chenxinli
 * @Date: 2020/7/29 18:04
 * @Description:
 */
@Service
public class JudgeService  extends BaseService<JudgeRepository, JudgeInfo, Integer> implements IJudgeService {

    @Override
    public List<JudgeInfo> getJudgeInfosByMeetingType(String meetingType) {
        List<JudgeInfo> judgeInfos = (List<JudgeInfo>) repository.findAll();
        return judgeInfos.stream().filter(judgeInfo -> judgeInfo.contains(meetingType)).collect(Collectors.toList());
    }

}
