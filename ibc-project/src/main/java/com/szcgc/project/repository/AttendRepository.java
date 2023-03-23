package com.szcgc.project.repository;

import com.szcgc.file.constant.FileCateEnum;
import com.szcgc.file.model.FileInfo;
import com.szcgc.project.model.AttendInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * @Author liaohong
 * @create 2020/9/18 10:21
 */
public interface AttendRepository extends PagingAndSortingRepository<AttendInfo, Integer> {

    List<AttendInfo> findByProjectId(int projectId);

    AttendInfo findFirstByProjectIdOrderByIdDesc(int projectId);

    List<AttendInfo> findByMeetingId(int meetingId);

//    @Query("select a from AttendInfo a where a.meetingId=0 and a.opinion in (?1)")
//    List<AttendInfo> findByOpinion(Collection<String> options);
//
//    @Query("select a from AttendInfo a where a.meetingId=0 and a.opinion in (?1)")
//    List<AttendInfo> findByOpinionIn(Collection<String> options);

    @Modifying
    @Transactional
    @Query("update AttendInfo p set p.meetingId=0, p.hostId=0, p.judgeIds='', p.meetingOrder=0, p.confirmed=0 WHERE p.id=?1")
    void clearByMeetingId(int meetingId);

    int countByProjectId(int projectId);
}
