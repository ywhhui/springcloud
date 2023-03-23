package com.szcgc.cougua.service;

import com.szcgc.comm.IbcService;
import com.szcgc.cougua.model.IndividualInfo;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/9/1 16:51
 */
public interface IIndividualService extends IbcService<IndividualInfo, Integer> {

    List<IndividualInfo> findByProjectId(Integer projectId);

}
