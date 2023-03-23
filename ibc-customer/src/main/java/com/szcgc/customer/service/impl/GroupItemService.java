package com.szcgc.customer.service.impl;

import com.szcgc.comm.service.BaseService;
import com.szcgc.comm.util.SundryUtils;
import com.szcgc.customer.model.GroupItemInfo;
import com.szcgc.customer.repository.GroupItemRepository;
import com.szcgc.customer.service.IGroupItemService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author liaohong
 * @create 2022/9/17 16:41
 */
@Service
public class GroupItemService extends BaseService<GroupItemRepository, GroupItemInfo, Integer> implements IGroupItemService {

    @Override
    public GroupItemInfo findByCustomerId(int customerId) {
        return repository.findByCustomerId(SundryUtils.requireId(customerId));
    }

    @Override
    public List<GroupItemInfo> findByMainId(int mainId) {
        return repository.findByMainId(SundryUtils.requireId(mainId));
    }

    @Override
    public void deleteByCustomerId(int customerId) {
        repository.deleteByCustomerId(SundryUtils.requireId(customerId));
    }
}
