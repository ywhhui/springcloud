package com.szcgc.project.service;

import com.szcgc.comm.IbcService;
import com.szcgc.project.model.CapitalRegister;
import com.szcgc.project.vo.loan.ContractVo;
import com.szcgc.project.vo.loan.LoanApplyInfoVo;
import com.szcgc.project.vo.loan.LoanInfoVo;

import java.util.List;

/**
 * 资金成本登记接口
 *
 * @author chenjiaming
 * @date 2022-10-24 08:56:35
 */
public interface ICapitalRegisterService extends IbcService<CapitalRegister, Integer> {

    /**
     * 获取放款申请信息
     *
     * @param projectId 项目id
     * @param loanId    放款id
     * @return 放款申请信息
     */
    LoanApplyInfoVo loanApplyInfo(Integer projectId, Integer loanId);

    /**
     * 获取放款信息数据
     *
     * @param loanId 放款id
     * @return 放款信息数据
     */
    List<LoanInfoVo> loanInfo(Integer loanId);

    /**
     * 获取合同信息数据
     *
     * @param projectId 项目id
     * @return 合同信息
     */
    List<ContractVo> contract(Integer projectId);

    /**
     * 获取登记信息
     *
     * @param projectId 项目id
     * @return 登记信息
     */
    CapitalRegister info(Integer projectId);

    /**
     * 提交
     *
     * @param accountId       当前用户
     * @param projectId       项目id
     * @param taskId          任务id
     * @param remark          备注
     * @param capitalRegister 登记信息
     */
    void submit(int accountId, int projectId, String taskId, String remark, CapitalRegister capitalRegister);
}
