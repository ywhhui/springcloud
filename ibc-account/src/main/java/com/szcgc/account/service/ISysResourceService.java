package com.szcgc.account.service;

import com.szcgc.account.model.permission.SysResource;
import com.szcgc.comm.IbcService;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/8/26 10:54
 */
public interface ISysResourceService extends IbcService<SysResource, Integer> {

    List<SysResource> findAll();
}
