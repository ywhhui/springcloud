package com.szcgc.account.service;

import com.szcgc.account.model.system.SettingInfo;
import com.szcgc.comm.IbcService;

import java.util.List;

/**
 * @Author liaohong
 * @create 2021/3/22 16:17
 */
public interface ISettingService extends IbcService<SettingInfo, Integer> {

    List<SettingInfo> findAll();

    List<SettingInfo> findByCate(String cate);

    //List<SettingInfo> findByCateAndKey(String cate,String key);

    void init();
}
