package com.szcgc.finance.service.analysis;


import com.szcgc.comm.IbcService;
import com.szcgc.finance.model.analysis.BalanceSheetSimple;
import com.szcgc.finance.model.base.BalanceSheet;

import java.util.List;

/**
 * 资产负债简表业务接口
 *
 * @author chenjiaming
 * @date 2022-9-27 14:35:19
 */
public interface BalanceSheetSimpleService extends IbcService<BalanceSheetSimple, Integer> {

    /**
     * 生成资产负债简表数据
     *
     * @param balanceSheets 资产负债表原始数据
     * @param mainId        主表id
     */
    void createSimpleTable(List<BalanceSheet> balanceSheets, Integer mainId, Integer accountId);

    /**
     * 根据主表id获取资产负债简表集合
     *
     * @param mainId 主表id
     * @return 资产负债简表集合
     */
    List<BalanceSheetSimple> findByMainId(Integer mainId);

    /**
     * 导出数据到报告
     *
     * @param mainId 主表id
     * @param path   报告路径
     */
    void export(Integer mainId, String path) throws Exception;
}
