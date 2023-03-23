package com.szcgc.project.service;

import com.szcgc.comm.IbcService;
import com.szcgc.project.model.ReleaseDetailInfo;

import java.util.List;

/**
 * 待解保抵押物详情
 */
public interface IReleaseDetailService extends IbcService<ReleaseDetailInfo, Integer> {

    List<ReleaseDetailInfo> findByProjectId(Integer projectId);

    ReleaseDetailInfo insert(ReleaseDetailInfo detailInfo);

    ReleaseDetailInfo update(ReleaseDetailInfo detailInfo);

    boolean releaseGuarantee(int projectId,String releaseType);

    void addReleaseDetail(int projectId);

}
