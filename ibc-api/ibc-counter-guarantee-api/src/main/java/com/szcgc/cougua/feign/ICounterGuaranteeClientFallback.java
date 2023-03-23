package com.szcgc.cougua.feign;

import com.szcgc.comm.exception.BaseException;
import com.szcgc.cougua.model.CorporationInfo;
import com.szcgc.cougua.model.MaterialInfo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author chenjiaming
 * @date 2022-9-23 14:42:03
 */
@Component
public class ICounterGuaranteeClientFallback implements ICounterGuaranteeClient {

    @Override
    public boolean searchAssessed(Integer projectId) {
        throw new BaseException("根据项目id 筛选 房地产抵押 建设用地使用权 股票 获取评估列表相关信息异常");
    }

    @Override
    public int divideAssessed(Integer projectId) {
        throw new BaseException("根据项目id获取分配的评估师相关信息异常");
    }

    @Override
    public List<MaterialInfo> guaranteeList(Integer projectId) {
        throw new BaseException("根据项目id获取所有添加的反担保措施异常");
    }

    @Override
    public boolean assessedTotal(Integer projectId) {
        throw new BaseException("根据项目id获取所有评估物总价是否超3000万异常");
    }

    @Override
    public List<CorporationInfo> corporationList(Integer projectId) {
        throw new BaseException("根据项目id获取所有添加的企业保证异常");
    }
}
