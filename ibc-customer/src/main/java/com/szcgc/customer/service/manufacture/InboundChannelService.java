package com.szcgc.customer.service.manufacture;

import com.szcgc.comm.IbcService;
import com.szcgc.customer.model.manufacture.InboundChannel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 进货渠道业务接口
 *
 * @author chenjiaming
 * @date 2022-9-22 09:42:39
 */
public interface InboundChannelService extends IbcService<InboundChannel, Integer> {

    /**
     * 根据项目id,客户id获取进货渠道列表
     *
     * @param projectId 项目id
     * @param custId    客户id
     * @return 进货渠道列表
     */
    List<InboundChannel> list(Integer projectId, Integer custId);

    /**
     * 进货渠道表导入
     *
     * @param file      文件
     */
    List<InboundChannel> importData( MultipartFile file) throws Exception;

    /**
     * 导出数据到报告
     *
     * @param projectId 项目id
     * @param custId    客户id
     * @param path      报告路径
     */
    void export(Integer projectId, Integer custId, String path) throws Exception;
}
