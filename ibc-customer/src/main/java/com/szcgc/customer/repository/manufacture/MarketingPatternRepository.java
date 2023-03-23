package com.szcgc.customer.repository.manufacture;

import com.szcgc.customer.model.manufacture.MarketingPattern;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketingPatternRepository extends PagingAndSortingRepository<MarketingPattern, Integer> {


    /**
     * 根据项目id,客户id获取营销模式列表
     *
     * @param projectId 项目id
     * @param custId    客户id
     * @return 营销模式列表
     */
    List<MarketingPattern> getMarketingPatternByProjectIdAndCustomerId(Integer projectId, Integer custId);
}
