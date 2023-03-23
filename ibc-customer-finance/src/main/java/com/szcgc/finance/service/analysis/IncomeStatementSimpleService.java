package com.szcgc.finance.service.analysis;


import com.szcgc.comm.IbcService;
import com.szcgc.finance.model.analysis.IncomeStatementSimple;
import com.szcgc.finance.model.base.IncomeStatement;

import java.util.List;

/**
 * 财务分析简表业务接口
 *
 * @author chenjiaming
 * @date 2022-9-27 14:36:40
 */
public interface IncomeStatementSimpleService extends IbcService<IncomeStatementSimple, Integer> {

    /**
     * 生成资产负债简表数据
     *
     * @param incomeStatements 损益表原始数据
     * @param mainId           主表id
     */
    void createSimpleTable(List<IncomeStatement> incomeStatements, Integer mainId, Integer accountId);

    /**
     * 根据主表id获取损益简表集合
     *
     * @param mainId 主表id
     * @return 损益简表集合
     */
    List<IncomeStatementSimple> findByMainId(Integer mainId);

    /**
     * 导出数据到报告
     *
     * @param mainId 主表id
     * @param path   报告路径
     */
    void export(Integer mainId, String path) throws Exception;
}
