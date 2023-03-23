package com.szcgc.project.repository;

import com.szcgc.project.model.OriginalInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @Author liaohong
 * @create 2020/9/25 17:36
 */
public interface OriginalRepository extends PagingAndSortingRepository<OriginalInfo, Integer> {
}
