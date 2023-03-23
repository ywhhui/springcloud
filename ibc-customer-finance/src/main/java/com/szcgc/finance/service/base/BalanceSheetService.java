package com.szcgc.finance.service.base;


import com.szcgc.comm.IbcService;
import com.szcgc.finance.model.base.BalanceSheet;
import com.szcgc.finance.vo.FinanceCustomerVo;

import java.util.List;

/**
 * 资产负债表业务接口
 *
 * @author chenjiaming
 * @date 2022-9-23 09:22:14
 */
public interface BalanceSheetService extends IbcService<BalanceSheet, Integer> {

    /**
     * 模糊查询有资产负债表数据的客户
     *
     * @param keyword 模糊关键字
     * @return 有资产负债表数据的客户集合
     */
    List<FinanceCustomerVo> search(String keyword);

    /**
     * 根据客户id获取历史资产负债表数据
     *
     * @param custId    客户id
     * @param projectId 项目id
     * @return 资产负债表数据集合
     */
    List<BalanceSheet> list(Integer custId, Integer projectId);

    /**
     * 根据项目id,客户id,年月获取资产负债表数据
     *
     * @param projectId 项目id
     * @param custId    客户id
     * @param date      年月
     * @return 资产负债表数据
     */
    BalanceSheet findByProjectIdAndCustomerIdAndDate(Integer projectId, Integer custId, String date);

    /**
     * 导入历史数据
     *
     * @param accountId  当前用户id
     * @param ids        导入数据id
     * @param projectId  项目id
     * @param customerId 客户id
     */
    void historyInsert(int accountId, List<Integer> ids, Integer projectId, Integer customerId);

    /**
     * 计算值
     *
     * @param balanceSheetList 资产负债表数据
     * @return 计算相关值后的资产负债表数据
     */
    List<BalanceSheet> calcValue(List<BalanceSheet> balanceSheetList);
}
