package com.szcgc.cougua.service;

import com.szcgc.comm.IbcService;
import com.szcgc.cougua.model.MaterialSubmitInfo;

/**
 * @Author liaohong
 * @create 2020/9/1 16:51
 */
public interface IMaterialSubmitService extends IbcService<MaterialSubmitInfo, Integer> {

    MaterialSubmitInfo findByProjectId(Integer projectId);

    MaterialSubmitInfo insert(MaterialSubmitInfo materialInfo);

}
