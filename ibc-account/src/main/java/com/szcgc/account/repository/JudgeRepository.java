package com.szcgc.account.repository;

import com.szcgc.account.model.system.JudgeInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @Author: chenxinli
 * @Date: 2020/7/29 18:00
 * @Description:
 */
public interface JudgeRepository extends PagingAndSortingRepository<JudgeInfo,Integer> {

}
