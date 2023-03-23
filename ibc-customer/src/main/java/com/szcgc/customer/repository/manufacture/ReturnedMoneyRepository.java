package com.szcgc.customer.repository.manufacture;

import com.szcgc.customer.model.manufacture.ReturnedMoney;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReturnedMoneyRepository extends PagingAndSortingRepository<ReturnedMoney, Integer> {


    /**
     * 根据项目id,客户id获取回款核实列表
     *
     * @param projectId 项目id
     * @param custId    客户id
     * @return 回款核实列表
     */
    List<ReturnedMoney> getReturnedMoneyByProjectIdAndCustomerId(Integer projectId, Integer custId);
}
