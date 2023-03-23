package com.szcgc.account.service;

import com.szcgc.account.model.system.BusinessConfigInfo;
import com.szcgc.comm.IbcService;

/**
 * @Author liaohong
 * @create 2020/9/4 16:04
 */
public interface IBusinessCfgService extends IbcService<BusinessConfigInfo, Integer> {

    BusinessConfigInfo findByBusinessType(String businessType);
}
