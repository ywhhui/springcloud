package com.szcgc.project.service;

import com.szcgc.comm.IbcService;
import com.szcgc.project.model.RepayPlan;
import com.szcgc.project.vo.repay.RepayBaseInfoVo;
import com.szcgc.project.vo.repay.RepayPlanVo;

import java.util.List;

/**
 * 还款计划接口
 *
 * @author chenjiaming
 * @date 2022-10-24 08:56:35
 */
public interface IRepayPlanService extends IbcService<RepayPlan, Integer> {

    /**
     * 根据项目id、还款id获取还款计划
     *
     * @param projectId 项目id
     * @param loanId    还款id
     * @return 还款计划
     */
    List<RepayPlanVo> list(Integer projectId, Integer loanId);

    /**
     * 变更还款计划
     *
     * @param list      还款计划列表
     * @param accountId 当前用户id
     */
    String change(List<RepayPlan> list, int accountId);

    /**
     * 还款入账
     *
     * @param list 入账数据
     */
    String credited(List<RepayPlan> list);

    /**
     * 删除还款计划
     *
     * @param id        还款计划id
     * @param accountId 当前用户id
     */
    String del(Integer id, Integer accountId);

    /**
     * 获取还款计划基本信息
     *
     * @param projectId 项目id
     * @param loanId    放款id
     * @return 基本信息
     */
    RepayBaseInfoVo baseInfo(Integer projectId, Integer loanId);
}
