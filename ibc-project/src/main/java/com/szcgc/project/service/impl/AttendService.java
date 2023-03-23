package com.szcgc.project.service.impl;

import com.szcgc.comm.service.BaseService;
import com.szcgc.comm.util.SundryUtils;
import com.szcgc.project.model.AttendInfo;
import com.szcgc.project.repository.AttendRepository;
import com.szcgc.project.service.IAttendService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/9/17 17:58
 */
@Service
public class AttendService extends BaseService<AttendRepository, AttendInfo, Integer> implements IAttendService {

    public List<AttendInfo> findByProjectId(int projectId) {
        return repository.findByProjectId(SundryUtils.requireId(projectId));
    }

    @Override
    public AttendInfo findLastByProjectId(int projectId) {
        return repository.findFirstByProjectIdOrderByIdDesc(SundryUtils.requireId(projectId));
    }

//    @Override
//    public List<AttendInfo> findByOpinion(String opinion) {
//        return null;
//    }
//
//    @Override
//    public List<AttendInfo> findByOpinions(List<String> opinions) {
//        return null;
//    }

    @Override
    public List<AttendInfo> findByMeeting(int meetingId) {
        return repository.findByMeetingId(meetingId);
    }

    @Override
    public void clear(int meetingId) {
        repository.clearByMeetingId(meetingId);
    }

    @Override
    public int countTimes(int projectId) {
        return repository.countByProjectId(SundryUtils.requireId(projectId));
    }
}
