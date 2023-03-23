package com.szcgc.cougua.service;

import com.szcgc.comm.IbcService;
import com.szcgc.cougua.model.IndividualDetailInfo;

import java.util.List;
import java.util.Optional;

/**
 * @Author liaohong
 * @create 2020/9/1 16:51
 */
public interface IIndividualDetailService extends IbcService<IndividualDetailInfo, Integer> {

    List<IndividualDetailInfo> findByProjectIdAndIndividualId(Integer projectId,Integer individualId);

    IndividualDetailInfo insert(IndividualDetailInfo detailInfo);

    IndividualDetailInfo update(IndividualDetailInfo detailInfo);

    Optional<IndividualDetailInfo> find(int id);
}
