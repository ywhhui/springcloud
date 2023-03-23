package com.szcgc.customer.repository;

import com.szcgc.customer.model.GroupItemInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/9/1 17:10
 */
public interface GroupItemRepository extends PagingAndSortingRepository<GroupItemInfo, Integer> {

    GroupItemInfo findByCustomerId(int customerId);

    //List<GroupItemInfo> findByGroupId(int groupId);

    List<GroupItemInfo> findByMainId(int mainId);


    //@Query("delete from GroupItemInfo where customerId=?1")
    @Modifying
    @Transactional
    void deleteByCustomerId(int customerId);

}
