package com.szcgc.customer.repository.manufacture;

import com.szcgc.customer.model.manufacture.ProductStructure;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductStructureRepository extends PagingAndSortingRepository<ProductStructure, Integer> {


    /**
     * 根据项目id,客户id获取产品结构列表
     *
     * @param projectId 项目id
     * @param custId    客户id
     * @return 产品结构列表
     */
    List<ProductStructure> getProductStructureByProjectIdAndCustomerId(Integer projectId, Integer custId);
}
