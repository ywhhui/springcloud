package com.szcgc.account.service;

import com.szcgc.account.model.system.BankBranchInfo;
import com.szcgc.comm.IbcService;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/9/23 15:27
 */
public interface IBankBranchService extends IbcService<BankBranchInfo, Integer> {

    List<BankBranchInfo> findAll();

    List<BankBranchInfo> findByBankId(int bankId);

    String findName(int id);

    String getZiJinBankName(int bankBranchId);

}
