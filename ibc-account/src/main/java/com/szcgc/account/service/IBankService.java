package com.szcgc.account.service;

import com.szcgc.account.model.system.BankInfo;
import com.szcgc.comm.IbcService;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/9/23 15:26
 */
public interface IBankService extends IbcService<BankInfo, Integer> {

    List<BankInfo> findAll();

    String findName(int id);

}
