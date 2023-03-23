package com.szcgc.cougua.feign;


import com.alibaba.nacos.common.utils.CollectionUtils;
import com.szcgc.cougua.model.CorporationInfo;
import com.szcgc.cougua.model.MaterialInfo;
import com.szcgc.cougua.model.MaterialSubmitInfo;
import com.szcgc.cougua.service.ICorporationService;
import com.szcgc.cougua.service.IMaterialService;
import com.szcgc.cougua.service.IMaterialSubmitService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 反担保feign相关
 */
@RestController
public class CounterGuaranteeClient implements ICounterGuaranteeClient {

    private static final Log logger = LogFactory.getLog(ICounterGuaranteeClient.class);

    @Autowired
    IMaterialService materialService;

    @Autowired
    IMaterialSubmitService materialSubmitService;

    @Autowired
    ICorporationService corporationService;

    @Override
    @GetMapping(SEARCH_ASSESSED)
    public boolean searchAssessed(@RequestParam("projectId") Integer projectId) {
        logger.info("searchAssessed入参 searchAssessed param:"+projectId);
        List<MaterialInfo> result = materialService.searchByProjectId(projectId);
        if(CollectionUtils.isNotEmpty(result)){
            return true;
        }
        return false;
    }

    @Override
    @GetMapping(DIVIDE_ASSESS)
    public int divideAssessed(@RequestParam("projectId") Integer projectId) {
        logger.info("divideAssessed  param:"+projectId);
        MaterialSubmitInfo result = materialSubmitService.findByProjectId(projectId);
        if(null != result){
            return result.getAssessingAccountId();
        }
        return 0;
    }

    @Override
    @GetMapping(GUARANTEE_LIST)
    public List<MaterialInfo> guaranteeList(@RequestParam("projectId") Integer projectId) {
        logger.info("guaranteeList projectId param:"+projectId);
        List<MaterialInfo> result = materialService.findByProjectId(projectId);
        return result;
    }

    @Override
    @GetMapping(ASSESSED_TOTAL)
    public boolean assessedTotal(@RequestParam("projectId") Integer projectId) {
        logger.info("assessedTotal入参 assessedTotal projectId:"+projectId);
        List<MaterialInfo> result = materialService.searchByProjectId(projectId);
        Double sum =result.stream().mapToDouble(e->null ==e.getGuaranteeLimit()?Double.valueOf(0): e.getGuaranteeLimit().doubleValue()).sum();
        logger.info("assessedTotal guaranteeLimit sum:"+sum);
        if(sum.compareTo(Double.valueOf("30000000")) >= 0){
            return true;
        }
        return false;
    }

    /**
     * 根据项目id获取所有添加的企业保证
     * @param projectId  项目id
     * @return 所有添加的企业集合
     */
    @Override
    @GetMapping(CORPORATION_LIST)
    public List<CorporationInfo> corporationList(@RequestParam("projectId") Integer projectId) {
        logger.info("corporationList projectId:"+projectId);
        return corporationService.findByProjectId(projectId);
    }
}
