package com.szcgc.customer.service;

import com.szcgc.comm.IbcService;
import com.szcgc.customer.model.Company;

import java.util.List;

/**
 * 公司信息业务接口
 *
 * @Author chenjiaming
 * @create 2022-9-20 17:18:02
 */
public interface CompanyService extends IbcService<Company, Integer> {

    Company findByCustId(Integer custId);

    /**
     * 根据天眼查企业id获取客户信息
     *
     * @param tids 天眼查企业id
     * @return 客户信息
     */
    List<Company> findByTidIn(List<Long> tids);
}
