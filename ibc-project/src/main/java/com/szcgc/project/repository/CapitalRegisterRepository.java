package com.szcgc.project.repository;

import com.szcgc.project.model.CapitalRegister;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 资金成本登记数据交互类
 *
 * @author chenjiaming
 * @date 2022-11-4 09:36:24
 */
public interface CapitalRegisterRepository extends PagingAndSortingRepository<CapitalRegister, Integer> {

    CapitalRegister findTop1ByProjectIdOrderByUpdateAtDesc(Integer projectId);
}
