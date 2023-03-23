package com.szcgc.customer.service.manufacture;

import com.szcgc.comm.IbcService;
import com.szcgc.customer.model.manufacture.ProductStructure;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 产品结构业务接口
 *
 * @author chenjiaming
 * @date 2022-9-20 17:18:02
 */
public interface ProductStructureService extends IbcService<ProductStructure, Integer> {

    /**
     * 根据项目id,客户id获取产品结构列表
     *
     * @param projectId 项目id
     * @param custId    客户id
     * @return 产品结构列表
     */
    List<ProductStructure> list(Integer projectId, Integer custId);

    /**
     * 产品结构表导入
     *
     * @param file 文件
     */
    List<ProductStructure> importData(MultipartFile file) throws Exception;

    /**
     * 导出数据到报告
     *
     * @param projectId 项目id
     * @param custId    客户id
     * @param path      报告路径
     */
    void export(Integer projectId, Integer custId, String path) throws Exception;
}
