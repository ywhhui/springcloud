package com.szcgc.project.service;

import com.szcgc.comm.IbcService;
import com.szcgc.project.model.RepayCertificateInfo;

/**
 * @Author liaohong
 * @create 2020/10/21 11:26
 */
public interface IRepayCertificateService extends IbcService<RepayCertificateInfo, Integer> {

    RepayCertificateInfo findLastByProjectId(int projectId);
}
