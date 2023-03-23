package com.szcgc.project.repository;

import com.szcgc.project.model.LoanApprovalInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/10/20 10:41
 */
public interface LoanApprovalRepository extends PagingAndSortingRepository<LoanApprovalInfo, Integer> {

    List<LoanApprovalInfo> findByProjectId(int projectId);

    List<LoanApprovalInfo> findByCustomerId(int customerId);

    LoanApprovalInfo findFirstByProjectIdOrderByIdDesc(int projectId);
}
