package com.szcgc.customer.service;

import com.szcgc.comm.IbcService;
import com.szcgc.customer.model.EquityStructure;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 股权结构业务接口
 *
 * @Author chenjiaming
 * @create 2022-9-29 18:09:04
 */
public interface EquityStructureService extends IbcService<EquityStructure, Integer> {

    /**
     * 根据项目id,客户id获取股权结构列表
     *
     * @param accountId 当前用户id
     * @param projectId 项目id
     * @param custId    客户id
     * @return 股权结构列表
     */
    List<EquityStructure> list(Integer accountId, Integer projectId, Integer custId);

    /**
     * 股权结构表导入
     *
     * @param file 文件
     */
    List<EquityStructure> importData(MultipartFile file) throws Exception;

    /**
     * 导出数据到报告
     *
     * @param projectId 项目id
     * @param custId    客户id
     * @param path      报告路径
     */
    void export(Integer projectId, Integer custId, String path) throws Exception;
}
