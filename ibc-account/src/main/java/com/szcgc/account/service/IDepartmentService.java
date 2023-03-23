package com.szcgc.account.service;

import com.szcgc.account.model.DepartmentInfo;
import com.szcgc.comm.IbcService;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/8/17 18:06
 */
public interface IDepartmentService extends IbcService<DepartmentInfo, Integer> {

    List<DepartmentInfo> findAll();

    String findName(int departId);

    void init();
}
