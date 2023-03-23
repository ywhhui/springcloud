package com.szcgc.cougua.feign;

import com.szcgc.comm.constant.AppConstant;
import com.szcgc.cougua.model.CorporationInfo;
import com.szcgc.cougua.model.MaterialInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 反担保模块api
 *
 * @author chenjiaming
 * @date 2022-9-23 14:41:26
 */
@FeignClient(
        value = AppConstant.APPLICATION_GOUNTER_GUARANTEE_NAME,
        fallback = ICounterGuaranteeClientFallback.class
)
public interface ICounterGuaranteeClient {

    String API_PREFIX = "/guarantee";
    String SEARCH_ASSESSED = API_PREFIX + "/assessed/project";
    String DIVIDE_ASSESS = API_PREFIX + "/divide/project";
    String GUARANTEE_LIST = API_PREFIX + "/list/project";
    String ASSESSED_TOTAL = API_PREFIX + "/total/project";
    String CORPORATION_LIST = API_PREFIX + "/corporation/project";

    /**
     * 根据项目id 筛选 房地产抵押 建设用地使用权 股票 获取评估列表相关信息
     * @param projectId  项目id
     * @return true 表示需要评估 false 不需要
     */
    @GetMapping(SEARCH_ASSESSED)
    boolean searchAssessed(@RequestParam("projectId") Integer projectId);

    /**
     * 根据项目id获取分配的评估师id
     * @param projectId  项目id
     * @return 分配的评估师id
     */
    @GetMapping(DIVIDE_ASSESS)
    int divideAssessed(@RequestParam("projectId") Integer projectId);

    /**
     * 根据项目id获取所有添加的反担保措施
     * @param projectId  项目id
     * @return 所有添加的反担保措施集合
     */
    @GetMapping(GUARANTEE_LIST)
    List<MaterialInfo> guaranteeList(@RequestParam("projectId") Integer projectId);

    /**
     * 根据项目id获取所有评估物总价是否超3000万
     * @param projectId  项目id
     * @return true 表示已经超过 false 没超过
     */
    @GetMapping(ASSESSED_TOTAL)
    boolean assessedTotal(@RequestParam("projectId") Integer projectId);

    /**
     * 根据项目id获取所有添加的企业保证
     * @param projectId  项目id
     * @return 所有添加的企业集合
     */
    @GetMapping(CORPORATION_LIST)
    List<CorporationInfo> corporationList(@RequestParam("projectId") Integer projectId);
}
