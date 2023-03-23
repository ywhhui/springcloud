package com.szcgc.customer.service.manufacture;

import com.szcgc.comm.IbcService;
import com.szcgc.customer.model.manufacture.SalesRevenue;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 销售收入业务接口
 *
 * @author chenjiaming
 * @date 2022-9-22 16:32:54
 */
public interface SalesRevenueService extends IbcService<SalesRevenue, Integer> {

    /**
     * 根据项目id,客户id获取销售收入列表
     *
     * @param projectId 项目id
     * @param custId    客户id
     * @return 销售收入列表
     */
    List<SalesRevenue> list(Integer projectId, Integer custId);

    /**
     * 销售收入表导入
     *
     * @param file      文件
     */
    List<SalesRevenue>  importData(MultipartFile file) throws Exception;


    /**
     * 导出数据到报告
     *
     * @param projectId 项目id
     * @param custId    客户id
     * @param path      报告路径
     */
    void export(Integer projectId, Integer custId, String path) throws Exception;
}
