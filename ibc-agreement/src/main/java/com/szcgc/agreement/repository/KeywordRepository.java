package com.szcgc.agreement.repository;

import com.szcgc.agreement.model.KeywordEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface KeywordRepository extends PagingAndSortingRepository<KeywordEntity, Long> {

	KeywordEntity findById(long id);
}
