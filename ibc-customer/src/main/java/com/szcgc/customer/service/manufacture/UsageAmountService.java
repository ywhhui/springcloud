package com.szcgc.customer.service.manufacture;

import com.szcgc.comm.IbcService;
import com.szcgc.customer.model.manufacture.UsageAmount;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 水煤电用量核实业务接口
 *
 * @author chenjiaming
 * @date 2022-9-22 16:32:54
 */
public interface UsageAmountService extends IbcService<UsageAmount, Integer> {

    /**
     * 根据项目id,客户id获取水煤电用量核实列表
     *
     * @param projectId 项目id
     * @param custId    客户id
     * @return 水煤电用量核实列表
     */
    List<UsageAmount> list(Integer projectId, Integer custId);

    /**
     * 水煤电用量核实表导入
     *
     * @param file      文件
     */
    List<UsageAmount> importData(MultipartFile file) throws Exception;
}
