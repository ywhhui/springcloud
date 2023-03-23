package com.szcgc.customer.repository.manufacture;

import com.szcgc.customer.model.manufacture.InboundChannel;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InboundChannelRepository extends PagingAndSortingRepository<InboundChannel, Integer> {


    /**
     * 根据项目id,客户id获取进货渠道列表
     *
     * @param projectId 项目id
     * @param custId    客户id
     * @return 进货渠道列表
     */
    List<InboundChannel> getInboundChannelByProjectIdAndCustomerId(Integer projectId, Integer custId);
}
