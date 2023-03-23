package com.szcgc.project.repository;

import com.szcgc.project.model.CommAuditInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/9/18 10:20
 */
public interface CommAuditRepository extends PagingAndSortingRepository<CommAuditInfo, Integer> {

    List<CommAuditInfo> findByProjectId(int projectId);

    CommAuditInfo findFirstByProjectIdOrderByIdDesc(int projectId);

//    List<AttendInfo> findByIdIn(Collection<Integer> ids);
//
//    AttendInfo findFirstByProjectIdOrderByIdDesc(int projectId);
//
//    @Modifying
//    @Transactional
//    @Query("update AttendInfo p set p.confirmed=?4, p.confirmBy=?3, p.versionTag=p.versionTag+1, p.confirmAt=now()  WHERE p.id in (?1) and p.projectId=?2")
//    int updateConfirms(List<Integer> ids, int projectId, int accountId, int confirmed);
//
//    List<AttendInfo> findByProjectIdAndCateAndRemarks(int projectId, FileCateEnum cate, String remarks);
//
//    List<AttendInfo> findByProjectIdAndCateIn(int projectId, Collection<FileCateEnum> cates);
}
