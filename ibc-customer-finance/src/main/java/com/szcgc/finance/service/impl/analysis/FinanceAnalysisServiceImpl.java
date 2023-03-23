package com.szcgc.finance.service.impl.analysis;

import com.szcgc.comm.service.BaseService;
import com.szcgc.finance.model.analysis.FinanceAnalysis;
import com.szcgc.finance.repository.analysis.FinanceAnalysisRepository;
import com.szcgc.finance.service.analysis.FinanceAnalysisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 财务分析主表业务实现类
 *
 * @author chenjiaming
 * @date 2022-9-27 14:39:54
 */
@Slf4j
@Service
public class FinanceAnalysisServiceImpl extends BaseService<FinanceAnalysisRepository, FinanceAnalysis, Integer> implements FinanceAnalysisService {

    @Override
    public FinanceAnalysis findTop1ByProjectIdAndCustomerIdOrderByCreateAtDesc(Integer projectId, Integer custId) {
        return repository.findTop1ByProjectIdAndCustomerIdOrderByCreateAtDesc(projectId, custId);
    }
}
