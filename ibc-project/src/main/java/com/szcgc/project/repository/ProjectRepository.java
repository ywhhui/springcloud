package com.szcgc.project.repository;

import com.szcgc.project.constant.BusinessTypeEnum;
import com.szcgc.project.constant.EnterprisesTypeEnum;
import com.szcgc.project.constant.ProjectStatusEnum;
import com.szcgc.project.model.ProjectInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface ProjectRepository extends PagingAndSortingRepository<ProjectInfo, Integer> {

    @Modifying
    @Transactional
    @Query("update ProjectInfo p set p.ibcStatus=:status, p.updateAt=now() WHERE p.id=:id")
    int updateStatus(@Param("id") Integer id, @Param("status") ProjectStatusEnum status);

    @Modifying
    @Transactional
    @Query("update ProjectInfo p set p.enterprisesType=:type, p.updateAt=now() WHERE p.id=:id")
    int updateEnterprisesType(@Param("id") Integer id, @Param("type") EnterprisesTypeEnum enterprisesType);

    @Modifying
    @Transactional
    @Query("update ProjectInfo p set p.amount=?2, p.during=?3, p.duringUnit=?4, p.businessType=?5, p.updateAt=now() WHERE p.id=?1")
    int updateBasic(int projectId, long amount, int during, int duringUnit, BusinessTypeEnum businessType);

    @Modifying
    @Transactional
    @Query("update ProjectInfo p set p.endDate=?2, p.updateAt=now() WHERE p.id=?1")
    int updateEndDate(int projectId, LocalDate endDate);

    @Modifying
    @Transactional
    @Query("update ProjectInfo p set p.currManual=?2, p.updateAt=now() WHERE p.id=?1")
    int updateManual(int projectId, String currManual);

    @Modifying
    @Transactional
    @Query("update ProjectInfo p set p.contYear=?2, p.contNo=?3 WHERE p.id=?1")
    int updateContInfo(int projectId, int contYear, String contNo);

    @Query("select sum(p.amount) from ProjectInfo p where p.customerId=?1 and p.ibcStatus=?2")
    long sumAmount(int customerId, ProjectStatusEnum status);

    ProjectInfo findFirstByCustomerIdOrderByIdDesc(int customerId);

    Page<ProjectInfo> findByCustomerId(int customerId, Pageable page);

    Page<ProjectInfo> findByCodeLike(String code, Pageable page);

    @Query("select p from ProjectInfo p where p.customerId=?1 and p.businessType in (?2) and p.ibcStatus in (?3)")
    Page<ProjectInfo> findByCustomerId(int customerId, List<BusinessTypeEnum> businessTypes,
                                       List<ProjectStatusEnum> statuses, Pageable page);

    Page<ProjectInfo> findByIbcStatusIn(Collection<ProjectStatusEnum> status, Pageable page);

    Page<ProjectInfo> findByCodeLikeAndIbcStatusIn(String code,
                                                   Collection<ProjectStatusEnum> status,
                                                   Pageable page);

    @Query(value = "select * from projectinfo where cont_year=?1 and cont_no like 'N%' order by REPLACE(cont_no,'N','')+0 desc limit 1", nativeQuery = true)
    ProjectInfo findMaxContNoByContYear(int contYear);

}
