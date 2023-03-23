package com.szcgc.finance.service.base;


import com.szcgc.comm.IbcService;
import com.szcgc.finance.model.base.CashFlowStatement;
import com.szcgc.finance.vo.FinanceCustomerVo;

import java.util.List;

/**
 * 现金流量表业务接口
 *
 * @author chenjiaming
 * @date 2022-9-23 09:22:14
 */
public interface CashFlowStatementService extends IbcService<CashFlowStatement, Integer> {

    /**
     * 模糊查询有现金流量表数据的客户
     *
     * @param keyword 模糊关键字
     * @return 有现金流量表数据的客户集合
     */
    List<FinanceCustomerVo> search(String keyword);

    /**
     * 根据客户id获取历史现金流量表数据
     *
     * @param custId 客户id
     * @return 现金流量表数据集合
     */
    List<CashFlowStatement> list(Integer custId, Integer projectId);

    /**
     * 根据项目id,客户id,年月获取现金流量表数据
     *
     * @param projectId 项目id
     * @param custId    客户id
     * @param date      年月
     * @return 现金流量表数据
     */
    CashFlowStatement findByProjectIdAndCustomerIdAndDate(Integer projectId, Integer custId, String date);

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
     * @param cashFlowStatementList 现金流量表
     * @return 计算后的集合
     */
    List<CashFlowStatement> calcValue(List<CashFlowStatement> cashFlowStatementList);
}
