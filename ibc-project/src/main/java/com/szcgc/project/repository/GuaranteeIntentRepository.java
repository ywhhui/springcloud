package com.szcgc.project.repository;

import com.szcgc.project.model.GuaranteeIntentInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/10/15 15:07
 */
public interface GuaranteeIntentRepository extends PagingAndSortingRepository<GuaranteeIntentInfo, Integer> {

    List<GuaranteeIntentInfo> findByProjectId(int projectId);

    GuaranteeIntentInfo findFirstByProjectIdOrderByIdDesc(int projectId);

    @Query("select count(c) from GuaranteeIntentInfo c where c.projectId=?1 and c.enrollRst=?2")
    Integer countProjectIdAndEnrollRst(int projectId, int enrollRst);
}
