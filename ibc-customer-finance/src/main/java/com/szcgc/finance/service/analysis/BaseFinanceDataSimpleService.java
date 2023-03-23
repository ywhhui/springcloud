package com.szcgc.finance.service.analysis;


import com.szcgc.comm.IbcService;
import com.szcgc.finance.model.analysis.BaseFinanceDataSimple;
import com.szcgc.finance.model.base.BalanceSheet;
import com.szcgc.finance.model.base.IncomeStatement;

import java.util.List;

/**
 * 基本财务数据简表业务接口
 *
 * @author chenjiaming
 * @date 2022-9-27 14:35:19
 */
public interface BaseFinanceDataSimpleService extends IbcService<BaseFinanceDataSimple, Integer> {

    /**
     * 生成基本财务简表数据
     *
     * @param balanceSheets    资产负债表原始数据
     * @param incomeStatements 损益表原始数据
     * @param mainId           主表id
     * @param accountId        当前用户id
     */
    void createSimpleTable(List<BalanceSheet> balanceSheets, List<IncomeStatement> incomeStatements, Integer mainId, Integer accountId);

    /**
     * 根据主表id获取基本财务数据简表集合
     *
     * @param mainId 主表id
     * @return 基本财务数据集合
     */
    List<BaseFinanceDataSimple> findByMainId(Integer mainId);

    /**
     * 导出数据到报告
     *
     * @param mainId 主表id
     * @param path   报告路径
     */
    void export(Integer mainId, String path) throws Exception;
}
