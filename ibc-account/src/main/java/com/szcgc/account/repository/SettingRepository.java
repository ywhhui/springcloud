package com.szcgc.account.repository;

import com.szcgc.account.model.system.SettingInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @Author liaohong
 * @create 2021/3/22 16:20
 */
public interface SettingRepository extends PagingAndSortingRepository<SettingInfo, Integer> {

    List<SettingInfo> findByCateAndSetKey(String cate, String key);

    List<SettingInfo> findByCate(String cate);
}
