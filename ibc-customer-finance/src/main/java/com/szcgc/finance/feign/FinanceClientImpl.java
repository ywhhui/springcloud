package com.szcgc.finance.feign;

import com.szcgc.finance.model.analysis.FinanceAnalysis;
import com.szcgc.finance.service.FinanceInfoService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class FinanceClientImpl implements FinanceClient {

    @Resource
    private FinanceInfoService financeService;

    @Override
    public FinanceAnalysis financeAnalysis(Integer projectId, Integer custId) {
        return financeService.detail(projectId, custId);
    }
}
