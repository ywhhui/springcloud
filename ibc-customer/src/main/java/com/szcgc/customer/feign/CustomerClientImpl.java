package com.szcgc.customer.feign;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.szcgc.comm.exception.BaseException;
import com.szcgc.comm.util.UsccUtil;
import com.szcgc.customer.constant.DicEnum;
import com.szcgc.customer.model.CustomerInfo;
import com.szcgc.customer.service.ICustomerService;
import com.szcgc.customer.util.ParamVerifyUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@RestController
public class CustomerClientImpl implements CustomerClient {

    @Resource
    private ICustomerService customerService;

    @Override
    @GetMapping(value = SEARCH_CUST)
    public List<CustomerInfo> searchCust(@RequestParam String keyword, @RequestParam List<Integer> custIds) {
        return customerService.searchCust(keyword, custIds);
    }

    @Override
    public CustomerInfo findCust(@RequestParam("idNo") String idNo) {
        CustomerInfo customerInfo = customerService.findByIdNo(idNo);
        return customerInfo;
    }

    @Override
    public CustomerInfo addCust(@RequestBody CustomerInfo customerInfo) {
        String errorTips = ParamVerifyUtil.verify(customerInfo);
        if (StrUtil.isNotBlank(errorTips)) {
            throw new BaseException(errorTips);
        }

        if (DicEnum.CUST_TYPE_PERSON.getValue() == customerInfo.getCate()) {
            if (ObjectUtil.isNull(customerInfo.getCertificateType())) {
                throw new BaseException("证件类型不能为空");
            }

            if (StrUtil.isNotBlank(customerInfo.getIdNo())) {
                errorTips = UsccUtil.isIdentityCode(customerInfo.getIdNo());
            } else {
                errorTips = "证件号不能为空";
            }

            customerInfo.setLegal(customerInfo.getName());
        }
        if (StrUtil.isNotBlank(errorTips)) {
            throw new BaseException(errorTips);
        }
        CustomerInfo existsCust = customerService.findByIdNo(customerInfo.getIdNo());
        if (ObjectUtil.isNotNull(existsCust)) {
            customerInfo.setId(existsCust.getId());
        }
        CustomerInfo custInfo = customerService.insert(customerInfo);

        return custInfo;
    }

    @Override
    public Optional<CustomerInfo> findCustById(@RequestParam("id") Integer id) {
        return customerService.find(id);
    }
}
