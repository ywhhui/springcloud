package com.szcgc.cougua.repository;

import com.szcgc.cougua.constant.CounterGuaranteeTypeCateEnum;
import com.szcgc.cougua.model.MaterialInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/9/1 17:10
 */
public interface MaterialRepository extends PagingAndSortingRepository<MaterialInfo, Integer> {

    List<MaterialInfo> findByProjectId(int projectId);

    List<MaterialInfo> findByProjectIdAndCate(int projectId,CounterGuaranteeTypeCateEnum cate);

}
