package com.szcgc.project.repository;

import com.szcgc.project.model.AgreementApprovalInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/11/20 11:14
 */
public interface AgreementApprovalRepository extends PagingAndSortingRepository<AgreementApprovalInfo, Integer> {

    List<AgreementApprovalInfo> findByProjectId(int projectId);

    AgreementApprovalInfo findFirstByProjectIdOrderByIdDesc(int projectId);

}
