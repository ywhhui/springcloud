package com.szcgc.account.repository;

import com.szcgc.account.model.permission.RoleResource;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/8/26 15:35
 */
public interface RoleResourceRepository extends PagingAndSortingRepository<RoleResource, Integer> {

    List<RoleResource> findBySysRoleId(int sysRoleId);

    @Modifying
    @Transactional
    int deleteBySysRoleId(int sysRoleId);

    @Query("select a.sysResourceId from RoleResource a where a.sysRoleId in (?1)")
    List<Integer> findSysResourceIds(Integer[] sysRoleIds);
}
