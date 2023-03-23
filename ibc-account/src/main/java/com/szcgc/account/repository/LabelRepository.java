package com.szcgc.account.repository;

import com.szcgc.account.model.system.LabelInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/10/20 16:03
 */
public interface LabelRepository extends PagingAndSortingRepository<LabelInfo, Integer> {

    List<LabelInfo> findByName(String name);

    List<LabelInfo> findByNameLike(String name);
}
