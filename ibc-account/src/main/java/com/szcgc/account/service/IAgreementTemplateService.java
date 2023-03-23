package com.szcgc.account.service;

import com.szcgc.account.model.system.AgreementTemplateInfo;
import com.szcgc.comm.IbcService;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/8/17 18:08
 */
public interface IAgreementTemplateService extends IbcService<AgreementTemplateInfo, Integer> {
    List<AgreementTemplateInfo> findAll();
}
