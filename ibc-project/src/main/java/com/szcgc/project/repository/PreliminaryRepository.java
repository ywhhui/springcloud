package com.szcgc.project.repository;

import com.szcgc.project.model.PreliminaryInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author; liaohong
 * @date: 2021/6/18 10:41
 * @description:
 */
public interface PreliminaryRepository extends PagingAndSortingRepository<PreliminaryInfo, Integer> {

}
