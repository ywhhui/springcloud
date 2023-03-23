package com.szcgc.customer.service.manufacture;

import com.szcgc.comm.IbcService;
import com.szcgc.customer.model.manufacture.MarketingPattern;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 营销模式业务接口
 *
 * @author chenjiaming
 * @date 2022-9-22 16:02:03
 */
public interface MarketingPatternService extends IbcService<MarketingPattern, Integer> {

    /**
     * 根据项目id,客户id获取营销模式列表
     *
     * @param projectId 项目id
     * @param custId    客户id
     * @return 营销模式列表
     */
    List<MarketingPattern> list(Integer projectId, Integer custId);

    /**
     * 营销模式表导入
     *
     * @param file      文件
     */
    List<MarketingPattern> importData( MultipartFile file) throws Exception;
}
