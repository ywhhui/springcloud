package com.szcgc.account.repository;

import com.szcgc.account.model.permission.SysRole;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @Author liaohong
 * @create 2020/8/26 15:36
 */
public interface SysRoleRepository extends PagingAndSortingRepository<SysRole, Integer> {
}
