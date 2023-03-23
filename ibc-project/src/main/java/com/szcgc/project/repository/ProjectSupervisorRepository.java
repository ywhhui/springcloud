package com.szcgc.project.repository;

import com.szcgc.project.model.ProjectSupervisorInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/9/18 10:21
 */
public interface ProjectSupervisorRepository extends PagingAndSortingRepository<ProjectSupervisorInfo, Integer> {

    ProjectSupervisorInfo findFirstByProjectIdOrderByIdDesc(int projectId);

    //    List<ProjectSupervisorInfo> findByProjectIdOrderByRole(int projectId);
//
//    ProjectSupervisorInfo findByProjectIdAndRoleAndIbcStatus(int projectId, SupervisorRoleEnum role, int ibcStatus);
//
    //@Query("select p from ProjectSupervisorInfo p where p.roleA=?1 and p.ibcStatus=?2")
    @Query("select p from ProjectSupervisorInfo p where p.roleAId=?1")
    Page<ProjectSupervisorInfo> findByAccountId(int accountId, Pageable page);
}
