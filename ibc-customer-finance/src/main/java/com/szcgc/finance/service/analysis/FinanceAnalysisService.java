package com.szcgc.finance.service.analysis;


import com.szcgc.comm.IbcService;
import com.szcgc.finance.model.analysis.FinanceAnalysis;

/**
 * 损益简表业务接口
 *
 * @author chenjiaming
 * @date 2022-9-27 14:36:40
 */
public interface FinanceAnalysisService extends IbcService<FinanceAnalysis, Integer> {

    /**
     * 根据项目id,客户id获取财务分析详情
     *
     * @param projectId 项目id
     * @param custId    客户id
     * @return 财务分析详情
     */
    FinanceAnalysis findTop1ByProjectIdAndCustomerIdOrderByCreateAtDesc(Integer projectId, Integer custId);
}
