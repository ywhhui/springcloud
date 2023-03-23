package com.szcgc.account.service;

import com.szcgc.account.model.BranchInfo;
import com.szcgc.comm.IbcService;

import java.util.List;

/**
 * @Author liaohong
 * @create 2021/3/19 10:33
 */
public interface IBranchService extends IbcService<BranchInfo, Integer> {

    List<BranchInfo> findAll();
}
