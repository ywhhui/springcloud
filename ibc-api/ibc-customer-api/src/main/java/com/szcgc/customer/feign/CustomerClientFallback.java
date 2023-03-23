package com.szcgc.customer.feign;

import com.szcgc.comm.exception.BaseException;
import com.szcgc.customer.model.CustomerInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author chenjiaming
 * @date 2022-9-23 14:42:03
 */
@Component
public class CustomerClientFallback implements CustomerClient {

    @Override
    public List<CustomerInfo> searchCust(String keyword, List<Integer> custIds) {
        throw new BaseException("根据指定客户id范围模糊查询企业名异常");
    }

    @Override
    public CustomerInfo findCust(String idNo) {
        throw new BaseException("根据统一社会信用代码/身份证号查找客户id异常");
    }

    @Override
    public CustomerInfo addCust(CustomerInfo customerInfo) {
        throw new BaseException("新增客户信息异常");
    }

    @Override
    public Optional<CustomerInfo> findCustById(Integer id) {
        throw new BaseException("根据id获取客户信息异常");
    }
}
