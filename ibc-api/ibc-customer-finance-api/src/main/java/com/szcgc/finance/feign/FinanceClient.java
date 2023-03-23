package com.szcgc.finance.feign;

import com.szcgc.comm.constant.AppConstant;
import com.szcgc.finance.model.analysis.FinanceAnalysis;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 财务模块api
 *
 * @author chenjiaming
 * @date 2022-10-27 15:34:20
 */
@FeignClient(
        value = AppConstant.APPLICATION_CUSTOMER_FIN_NAME,
        fallback = FinanceClientFallback.class
)
public interface FinanceClient {

    String API_PREFIX = "/cust-fin";
    String FINANCE_ANALYSIS = API_PREFIX + "/finance-analysis";

    /**
     * 获取财务分析数据
     *
     * @param projectId 项目id
     * @param custId    客户id
     * @return 财务分析数据
     */
    @GetMapping(FINANCE_ANALYSIS)
    FinanceAnalysis financeAnalysis(@RequestParam("projectId") Integer projectId, @RequestParam("custId") Integer custId);

}
