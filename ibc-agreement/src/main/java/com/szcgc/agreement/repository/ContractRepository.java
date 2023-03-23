package com.szcgc.agreement.repository;

import com.szcgc.agreement.model.ContractEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ContractRepository extends PagingAndSortingRepository<ContractEntity, Long> {

	ContractEntity findById(long id);

	ContractEntity findFirstByProjectCodeOrderByCreatedAtDesc(String projectCode);

	ContractEntity findBySerialNo(String serialNo);
}
