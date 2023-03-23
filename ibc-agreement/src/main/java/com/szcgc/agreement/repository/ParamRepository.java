package com.szcgc.agreement.repository;

import com.szcgc.agreement.model.ParamEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ParamRepository extends PagingAndSortingRepository<ParamEntity, Long> {

	ParamEntity findById(long id);
}
