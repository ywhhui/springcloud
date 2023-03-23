package com.szcgc.customer.repository.manufacture;

import com.szcgc.customer.model.manufacture.SalesRevenue;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesRevenueRepository extends PagingAndSortingRepository<SalesRevenue, Integer> {


    /**
     * 根据项目id,客户id获取销售收入列表
     *
     * @param projectId 项目id
     * @param custId    客户id
     * @return 销售收入列表
     */
    List<SalesRevenue> getSalesRevenueByProjectIdAndCustomerId(Integer projectId, Integer custId);
}
