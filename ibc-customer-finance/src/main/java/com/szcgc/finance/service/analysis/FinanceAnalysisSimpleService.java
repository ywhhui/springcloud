package com.szcgc.finance.service.analysis;


import com.szcgc.comm.IbcService;
import com.szcgc.finance.model.analysis.FinanceAnalysisSimple;
import com.szcgc.finance.model.base.BalanceSheet;
import com.szcgc.finance.model.base.CashFlowStatement;
import com.szcgc.finance.model.base.IncomeStatement;

import java.util.List;

/**
 * 财务分析简表业务接口
 *
 * @author chenjiaming
 * @date 2022-9-27 14:36:40
 */
public interface FinanceAnalysisSimpleService extends IbcService<FinanceAnalysisSimple, Integer> {

    /**
     * 生成财务分析简表数据
     *
     * @param balanceSheets      资产负债表原始数据
     * @param incomeStatements   损益表原始数据
     * @param cashFlowStatements 流量现金表原始数据
     * @param mainId             主表id
     */
    void createSimpleTable(List<BalanceSheet> balanceSheets, List<IncomeStatement> incomeStatements, List<CashFlowStatement> cashFlowStatements,
                           Integer mainId, Integer accountId);

    /**
     * 根据主表id获取财务分析简表集合
     *
     * @param mainId 主表id
     * @return 财务分析简表集合
     */
    List<FinanceAnalysisSimple> findByMainId(Integer mainId);

    /**
     * 导出数据到报告
     *
     * @param mainId 主表id
     * @param path   报告路径
     */
    void export(Integer mainId, String path) throws Exception;
}
