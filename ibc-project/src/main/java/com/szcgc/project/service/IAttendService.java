package com.szcgc.project.service;

import com.szcgc.comm.IbcService;
import com.szcgc.project.model.AttendInfo;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/9/17 17:03
 */
public interface IAttendService extends IbcService<AttendInfo, Integer> {

    int countTimes(int projectId);

    AttendInfo findLastByProjectId(int projectId);

//    List<AttendInfo> findByOpinion(String opinion);
//
//    List<AttendInfo> findByOpinions(List<String> opinions);

    List<AttendInfo> findByMeeting(int meetingId);

    void clear(int meetingId);
}
