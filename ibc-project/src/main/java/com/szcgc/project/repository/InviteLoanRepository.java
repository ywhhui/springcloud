package com.szcgc.project.repository;

import com.szcgc.project.model.InviteLoanInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author JINLINGXUAN
 * @create 2021-03-23
 */
public interface InviteLoanRepository extends PagingAndSortingRepository<InviteLoanInfo, Integer> {

    List<InviteLoanInfo> findByProjectId(int projectId);

    InviteLoanInfo findFirstByProjectIdOrderByIdDesc(int projectId);

}
