package com.szcgc.customer.service;

import com.szcgc.comm.IbcService;
import com.szcgc.customer.model.Custodian;

import java.util.List;

/**
 * 管户人业务接口
 *
 * @Author chenjiaming
 * @create 2022-10-10 14:29:14
 */
public interface CustodianService extends IbcService<Custodian, Integer> {

    /**
     * 根据客户id集合获取管护人集合
     *
     * @param custIds 客户id集合
     * @return 管护人集合
     */
    List<Custodian> findByCustomerIdIn(List<Integer> custIds);

    /**
     * 调整管护人
     *
     * @param accountId   当前用户id
     * @param custId      客户id
     * @param custodianId 管护人id
     */
    void edit(int accountId, Integer custId, Integer custodianId);

    /**
     * 根据管护人名称模糊查询
     *
     * @param custodianName 管护人名称
     * @return 管护人集合
     */
    List<Custodian> findByCustodianNameContaining(String custodianName);

    /**
     * 根据客户id以及管户业务类型获取管户数据
     *
     * @param custId   客户id
     * @return 管户数据
     */
    Custodian findTop1ByCustomerId(Integer custId);
}
