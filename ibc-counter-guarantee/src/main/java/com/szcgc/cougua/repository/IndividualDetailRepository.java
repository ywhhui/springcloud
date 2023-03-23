package com.szcgc.cougua.repository;

import com.szcgc.cougua.constant.CounterGuaranteeTypeCateEnum;
import com.szcgc.cougua.model.IndividualDetailInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/9/1 17:10
 */
public interface IndividualDetailRepository extends PagingAndSortingRepository<IndividualDetailInfo, Integer> {

    List<IndividualDetailInfo> findByProjectIdAndIndividualId(int projectId,int individualId);

    List<IndividualDetailInfo> findByProjectIdAndCate(int projectId,CounterGuaranteeTypeCateEnum cate);

}
