package com.szcgc.agreement.repository;

import com.szcgc.agreement.model.UnionEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UnionRepository extends PagingAndSortingRepository<UnionEntity, Long> {

	UnionEntity findById(long id);
}
