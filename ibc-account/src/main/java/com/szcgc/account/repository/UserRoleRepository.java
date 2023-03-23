package com.szcgc.account.repository;

import com.szcgc.account.model.permission.UserRole;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/8/26 15:36
 */
public interface UserRoleRepository extends PagingAndSortingRepository<UserRole, Integer> {

    List<UserRole> findBySysRoleId(int sysRoleId);

    List<UserRole> findByAccountId(int accountId);

    @Modifying
    @Transactional
    int deleteByAccountId(int accountId);
}
