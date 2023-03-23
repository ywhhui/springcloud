package com.szcgc.customer.feign;

import com.szcgc.comm.constant.AppConstant;
import com.szcgc.customer.model.CustomerInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

/**
 * 客户模块api
 *
 * @author chenjiaming
 * @date 2022-9-23 14:41:26
 */
@FeignClient(
        value = AppConstant.APPLICATION_CUSTOMER_NAME,
        fallback = CustomerClientFallback.class
)
public interface CustomerClient {

    String API_PREFIX = "/cust";
    String SEARCH_CUST = API_PREFIX + "/search-cust";
    String FIND_CUST = API_PREFIX + "/find-cust";
    String ADD_CUST = API_PREFIX + "/add";
    String FIND_BY_ID = API_PREFIX + "/find-by-id";

    /**
     * 根据指定客户id范围模糊查询企业名
     *
     * @param keyword 客户名称模糊查询条件
     * @param custIds 客户id集合
     * @return 客户集合
     */
    @GetMapping(value = SEARCH_CUST)
    List<CustomerInfo> searchCust(@RequestParam("keyword") String keyword, @RequestParam("custIds") List<Integer> custIds);

    /**
     * 根据统一社会信用代码/身份证号查找客户id
     *
     * @param idNo 统一社会信用代码/身份证号
     * @return 客户id
     */
    @GetMapping(value = FIND_CUST)
    CustomerInfo findCust(@RequestParam("idNo") String idNo);

    /**
     * 添加客户
     *
     * @param customerInfo 客户信息
     * @return 客户id
     */
    @PostMapping(value = ADD_CUST)
    CustomerInfo addCust(@RequestBody CustomerInfo customerInfo);

    /**
     * 根据id获取客户信息
     *
     * @param id 客户id
     * @return 客户信息
     */
    @GetMapping(value = FIND_BY_ID)
    Optional<CustomerInfo> findCustById(@RequestParam("id") Integer id);
}
