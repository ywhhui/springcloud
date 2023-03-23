package com.szcgc.account.service;

import com.szcgc.account.model.system.LabelInfo;
import com.szcgc.comm.IbcService;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/10/20 16:02
 */
public interface ILabelService extends IbcService<LabelInfo, Integer> {

    List<LabelInfo> findByName(String name);

    List<LabelInfo> searchByName(String name);

}
