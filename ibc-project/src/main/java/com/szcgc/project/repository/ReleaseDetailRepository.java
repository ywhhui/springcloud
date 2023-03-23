package com.szcgc.project.repository;

import com.szcgc.project.model.ReleaseDetailInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author JINLINGXUAN
 * @create 2021-03-23
 */
public interface ReleaseDetailRepository extends PagingAndSortingRepository<ReleaseDetailInfo, Integer> {

    List<ReleaseDetailInfo> findByProjectId(int projectId);

    ReleaseDetailInfo findFirstByProjectIdAndReleaseTypeOrderByIdDesc(int projectId,String releaseType);
}
