package com.szcgc.account.repository;

import com.szcgc.account.model.AccountInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AccountRepository extends PagingAndSortingRepository<AccountInfo, Integer> {

    AccountInfo findByNameAndPassword(String name, String pwd);

    AccountInfo findByIdAndPassword(int id, String pwd);

    AccountInfo findByName(String name);

    AccountInfo findByPhone(String phone);

    List<AccountInfo> findByRealName(String realName);

    List<AccountInfo> findByDepartmentId(int departmentId);

    List<AccountInfo> findByIbcStatus(int ibcStatus);

    @Modifying
    @Transactional
    @Query("update AccountInfo p set p.password=:pwd WHERE p.id=:id")
    int updatePassword(@Param("id") Integer id, @Param("pwd") String pwd);

    @Modifying
    @Transactional
    @Query("update AccountInfo p set p.avatar=:avatar WHERE p.id=:id")
    int updateAvatar(@Param("id") Integer id, @Param("avatar") String avatar);
}
