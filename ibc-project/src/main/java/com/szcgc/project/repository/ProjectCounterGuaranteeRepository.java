package com.szcgc.project.repository;

import com.szcgc.project.model.ProjectCounterGuaranteeInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * @Author liaohong
 * @create 2020/9/24 10:26
 */
public interface ProjectCounterGuaranteeRepository extends PagingAndSortingRepository<ProjectCounterGuaranteeInfo, Integer> {

    List<ProjectCounterGuaranteeInfo> findByProjectId(int projectId);

    List<ProjectCounterGuaranteeInfo> findByProjectIdAndGuaranteeing(int projectId, int guaranteeing);

    List<ProjectCounterGuaranteeInfo> findByCustomerId(int customerId);

    List<ProjectCounterGuaranteeInfo> findByCounterGuaranteeIdIn(Collection<Integer> cgIds);

    List<ProjectCounterGuaranteeInfo> findByIdIn(Collection<Integer> ids);

    @Query("select count(c) from ProjectCounterGuaranteeInfo c where c.counterGuaranteeId=?1")
    Integer countCounterGuarantee(int counterGuaranteeId);

    @Modifying
    @Transactional
    @Query("update ProjectCounterGuaranteeInfo p set p.guaranteeing=?2  WHERE p.projectId=?1")
    int updateGuaranteeings(int projectId, int guaranteeing);

    @Modifying
    @Transactional
    @Query("update ProjectCounterGuaranteeInfo p set p.checkBy=?2, p.checkAt=now(), p.guaranteeing=?4  WHERE p.id in (?1) and p.projectId=?3")
    int checkGuaranteeings(List<Integer> ids, int accountId, int projectId, int guaranteeing);

    @Modifying
    @Transactional
    @Query("update ProjectCounterGuaranteeInfo p set p.selectBy=?2, p.selectAt=now(), p.guaranteeing=?4  WHERE p.id in (?1) and p.projectId=?3")
    int selectGuaranteeings(List<Integer> ids, int accountId, int projectId, int guaranteeing);

    @Modifying
    @Transactional
    @Query("update ProjectCounterGuaranteeInfo p set p.projectId=-p.projectId, p.customerId=-p.customerId, p.counterGuaranteeId=-p.counterGuaranteeId  WHERE p.projectId=?1")
    int updateBak(int projectId);
}
