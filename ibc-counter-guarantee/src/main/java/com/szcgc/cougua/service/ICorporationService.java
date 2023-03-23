package com.szcgc.cougua.service;

import com.szcgc.comm.IbcService;
import com.szcgc.cougua.model.CorporationInfo;

import java.util.List;

public interface ICorporationService extends IbcService<CorporationInfo, Integer> {

    List<CorporationInfo> findByProjectId(Integer projectId);

    CorporationInfo insert(CorporationInfo corporationInfo);
}
