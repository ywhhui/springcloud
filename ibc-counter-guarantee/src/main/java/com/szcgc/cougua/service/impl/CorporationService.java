package com.szcgc.cougua.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.service.BaseService;
import com.szcgc.comm.util.JsonUtils;
import com.szcgc.cougua.model.CorporationInfo;
import com.szcgc.cougua.repository.CorporationRepository;
import com.szcgc.cougua.service.ICorporationService;
import com.szcgc.customer.constant.DicEnum;
import com.szcgc.customer.feign.CustomerClient;
import com.szcgc.customer.model.CustomerInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author liaohong
 * @create 2022/9/17 15:59
 */
@Service
public class CorporationService extends BaseService<CorporationRepository, CorporationInfo, Integer> implements ICorporationService {

    private static final Log logger = LogFactory.getLog(CorporationService.class);

    @Autowired
    CustomerClient customerClient;

    @Override
    public List<CorporationInfo> findByProjectId(Integer projectId) {
        List<CorporationInfo> result = repository.findByProjectId(projectId);
        if(CollectionUtils.isNotEmpty(result)){
            result = result.stream().sorted(Comparator.comparing(CorporationInfo::getId).reversed()).collect(Collectors.toList());
        }
        return result;
    }

    @Override
    public CorporationInfo insert(CorporationInfo corporationInfo) {
        List<CorporationInfo> byProjectIdAndIdNo = repository.findByProjectIdAndIdNo(corporationInfo.getProjectId(), corporationInfo.getIdNo());
        if (CollectionUtils.isNotEmpty(byProjectIdAndIdNo)) {
            return null;
        }
        try {
            //同步新增企业
            CustomerInfo customerInfo = new CustomerInfo();
            BeanUtil.copyProperties(corporationInfo, customerInfo);
            customerInfo.setCate(DicEnum.CUST_TYPE_COMPANY.getValue());
            CustomerInfo customerResult = customerClient.addCust(customerInfo);
            //初始化企业id
            corporationInfo.setCustomerId(customerResult.getId());
            logger.info("corporationInfo insert param"+ JsonUtils.toJSONString(corporationInfo));
        } catch (Exception e) {
            logger.info("corporationInfo insert error!"+e);
        }
        CorporationInfo result = repository.save(corporationInfo);
        return result;
    }

}
