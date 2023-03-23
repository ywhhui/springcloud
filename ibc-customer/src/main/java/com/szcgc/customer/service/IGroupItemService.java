package com.szcgc.customer.service;

import com.szcgc.comm.IbcService;
import com.szcgc.customer.model.GroupItemInfo;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/9/1 16:51
 */
public interface IGroupItemService extends IbcService<GroupItemInfo, Integer> {

    GroupItemInfo findByCustomerId(int customerId);

    List<GroupItemInfo> findByMainId(int mainId);

    void deleteByCustomerId(int customerId);

    //List<GroupItemInfo> findByGroupId(int groupId);

}
