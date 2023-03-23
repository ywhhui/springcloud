package com.szcgc.project.repository;

import com.szcgc.project.model.RepayCertificateInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @Author liaohong
 * @create 2020/10/21 11:27
 */
public interface RepayCertificateRepository extends PagingAndSortingRepository<RepayCertificateInfo, Integer> {

    RepayCertificateInfo findFirstByProjectIdOrderByIdDesc(int projectId);

}
