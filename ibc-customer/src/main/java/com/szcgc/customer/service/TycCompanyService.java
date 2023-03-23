package com.szcgc.customer.service;

import com.szcgc.comm.IbcService;
import com.szcgc.customer.model.TycCompany;

/**
 * 天眼查公司信息业务接口
 *
 * @Author chenjiaming
 * @create 2022-10-12 11:33:30
 */
public interface TycCompanyService extends IbcService<TycCompany, Integer> {

    TycCompany findTop1ByIdNoOrderByCreateAtDesc(String idNo);
}
