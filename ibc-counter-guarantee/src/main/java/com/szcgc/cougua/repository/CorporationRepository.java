package com.szcgc.cougua.repository;

import com.szcgc.cougua.model.CorporationInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CorporationRepository extends PagingAndSortingRepository<CorporationInfo, Integer> {

    List<CorporationInfo> findByProjectId(int projectId);

    List<CorporationInfo> findByProjectIdAndIdNo(int projectId,String idNo);

}
