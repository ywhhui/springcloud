package com.szcgc.finance.service.analysis;


import com.szcgc.comm.IbcService;
import com.szcgc.finance.model.analysis.CashFlowStatementSimple;
import com.szcgc.finance.model.base.CashFlowStatement;

import java.util.List;

/**
 * 流量现金简表业务接口
 *
 * @author chenjiaming
 * @date 2022-9-27 14:36:40
 */
public interface CashFlowStatementSimpleService extends IbcService<CashFlowStatementSimple, Integer> {

    /**
     * 生成现金流量简表
     *
     * @param cashFlowStatements 现金流量原始表数据
     * @param mainId             主表id
     */
    void createSimpleTable(List<CashFlowStatement> cashFlowStatements, Integer mainId, Integer accountId);

    /**
     * 根据主表id获取流量现金简表集合
     *
     * @param mainId 主表id
     * @return 流量现金简表集合
     */
    List<CashFlowStatementSimple> findByMainId(Integer mainId);
}
