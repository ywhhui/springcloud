package com.szcgc.finance.feign;

import com.szcgc.comm.exception.BaseException;
import com.szcgc.finance.model.analysis.FinanceAnalysis;
import org.springframework.stereotype.Component;

/**
 * @author chenjiaming
 * @date 2022-10-27 15:35:54
 */
@Component
public class FinanceClientFallback implements FinanceClient {

    @Override
    public FinanceAnalysis financeAnalysis(Integer projectId, Integer custId) {
        throw new BaseException("根据指定项目id、客户id获取财务分析数据异常");
    }
}
