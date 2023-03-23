package com.szcgc.customer.repository.manufacture;

import com.szcgc.customer.model.manufacture.UsageAmount;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsageAmountRepository extends PagingAndSortingRepository<UsageAmount, Integer> {


    /**
     * 根据项目id,客户id获取水煤电用量核实列表
     *
     * @param projectId 项目id
     * @param custId    客户id
     * @return 水煤电用量核实列表
     */
    List<UsageAmount> getUsageAmountByProjectIdAndCustomerId(Integer projectId, Integer custId);
}
