package com.szcgc.agreement.repository;

import com.szcgc.agreement.model.TemplateEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TemplateRepository extends PagingAndSortingRepository<TemplateEntity, Long> {

	TemplateEntity findById(long id);
}
