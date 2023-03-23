package com.szcgc.finance.repository;

import com.szcgc.finance.model.ExamineInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author liaohong
 * @create 2020/9/1 17:10
 */
public interface ExamineRepository extends PagingAndSortingRepository<ExamineInfo, Integer> {

    List<ExamineInfo> findByProjectId(int projectId);

    ExamineInfo findFirstByProjectIdOrderByIdDesc(int projectId);

    @Modifying
    @Transactional
    @Query("update ExamineInfo p set p.ibcStatus=?2 WHERE p.projectId=?1 and p.ibcStatus=?3")
    int update4Enroll(int projectId, int ibcStatus, int currStatus);

    @Modifying
    @Transactional
    @Query("update ExamineInfo p set p.ibcStatus=?2, p.auditAccountId=?3, p.auditAt=?4 WHERE p.projectId=?1 and p.ibcStatus=?5")
    int update4Audit(int projectId, int ibcStatus, int auditAccountId, LocalDateTime auditAt, int currStatus);

}
