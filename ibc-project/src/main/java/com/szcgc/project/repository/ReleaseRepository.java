package com.szcgc.project.repository;

import com.szcgc.project.model.ReleaseInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author JINLINGXUAN
 * @create 2021-03-23
 */
public interface ReleaseRepository extends PagingAndSortingRepository<ReleaseInfo, Integer> {

    List<ReleaseInfo> findByProjectId(int projectId);

    ReleaseInfo findFirstByProjectIdOrderByIdDesc(int projectId);

}
