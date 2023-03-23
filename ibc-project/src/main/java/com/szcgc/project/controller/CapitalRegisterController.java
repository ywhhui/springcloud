package com.szcgc.project.controller;

import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.util.JsonUtils;
import com.szcgc.comm.web.IbcId;
import com.szcgc.project.model.CapitalRegister;
import com.szcgc.project.service.ICapitalRegisterService;
import com.szcgc.project.vo.loan.ContractVo;
import com.szcgc.project.vo.loan.LoanApplyInfoVo;
import com.szcgc.project.vo.loan.LoanInfoVo;
import com.szcgc.project.vo.loan.SubmitVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 资金成本登记
 *
 * @author chenjiaming
 * @date 2022-11-4 09:41:15
 */
@Slf4j
@Api(tags = "资金成本登记")
@RestController
@RequestMapping("capital-register")
public class CapitalRegisterController {

    @Resource
    private ICapitalRegisterService capitalRegisterService;

    @Operation(summary = "获取放款申请信息")
    @GetMapping("loan-apply-info")
    public IbcResponse<LoanApplyInfoVo> loanApplyInfo(@ApiParam("项目id") @RequestParam Integer projectId, @ApiParam("放款id") @RequestParam Integer loanId) {
        log.info("获取放款申请信息开始,请求参数为:projectId:{},loanId:{}", projectId, loanId);
        long start = System.currentTimeMillis();
        try {
            LoanApplyInfoVo loanApplyInfoVo = capitalRegisterService.loanApplyInfo(projectId, loanId);
            log.info("获取放款申请信息结果为:{}", JsonUtils.toJSONString(loanApplyInfoVo));
            return IbcResponse.ok(loanApplyInfoVo);
        } catch (Exception e) {
            log.error("获取放款申请信息异常", e);
            return IbcResponse.error500("获取放款申请信息异常");
        } finally {
            log.info("获取放款申请信息结束,共耗时[{}]ms", System.currentTimeMillis() - start);
        }
    }

    @Operation(summary = "获取放款信息")
    @GetMapping("loan-info")
    public IbcResponse<List<LoanInfoVo>> loanInfo(@ApiParam("放款id") @RequestParam Integer loanId) {
        log.info("获取放款信息开始,请求参数为:loanId:{}", loanId);
        long start = System.currentTimeMillis();
        try {
            List<LoanInfoVo> loanInfoVos = capitalRegisterService.loanInfo(loanId);
            log.info("获取放款信息集合长度为:{}", loanInfoVos.size());
            return IbcResponse.ok(loanInfoVos);
        } catch (Exception e) {
            log.error("获取放款信息异常", e);
            return IbcResponse.error500("获取放款信息异常");
        } finally {
            log.info("获取放款信息结束,共耗时[{}]ms", System.currentTimeMillis() - start);
        }
    }

    @Operation(summary = "获取合同信息")
    @GetMapping("contract")
    public IbcResponse<List<ContractVo>> contract(@ApiParam("项目id") @RequestParam Integer projectId) {
        log.info("获取合同信息开始,请求参数为:projectId:{}", projectId);
        long start = System.currentTimeMillis();
        try {
            List<ContractVo> contractVos = capitalRegisterService.contract(projectId);
            log.info("获取合同信息集合长度为:{}", contractVos.size());
            return IbcResponse.ok(contractVos);
        } catch (Exception e) {
            log.error("获取合同信息异常", e);
            return IbcResponse.error500("获取合同信息异常");
        } finally {
            log.info("获取合同信息结束,共耗时[{}]ms", System.currentTimeMillis() - start);
        }
    }

    @Operation(summary = "获取登记信息")
    @GetMapping("info")
    public IbcResponse<CapitalRegister> info(@ApiParam("项目id") @RequestParam Integer projectId) {
        log.info("获取登记信息开始,请求参数为:projectId:{}", projectId);
        long start = System.currentTimeMillis();
        try {
            CapitalRegister capitalRegister = capitalRegisterService.info(projectId);
            log.info("获取登记信息结果为:{}", JsonUtils.toJSONString(capitalRegister));
            return IbcResponse.ok(capitalRegister);
        } catch (Exception e) {
            log.error("获取登记信息异常", e);
            return IbcResponse.error500("获取登记信息异常");
        } finally {
            log.info("获取登记信息结束,共耗时[{}]ms", System.currentTimeMillis() - start);
        }
    }

    @Operation(summary = "提交")
    @PostMapping("submit")
    public IbcResponse submit(@ApiParam(hidden = true) @IbcId int accountId, @RequestBody SubmitVo submitVo) {
        log.info("资金成本登记提交开始,请求参数为:{}", JsonUtils.toJSONString(submitVo));
        long start = System.currentTimeMillis();
        try {
            capitalRegisterService.submit(accountId, submitVo.getProjectId(), submitVo.getTaskId(), submitVo.getRemark(), submitVo.getCapitalRegister());
            return IbcResponse.ok();
        } catch (Exception e) {
            log.error("资金成本登记提交异常", e);
            return IbcResponse.error500("资金成本登记提交异常");
        } finally {
            log.info("资金成本登记提交结束,共耗时[{}]ms", System.currentTimeMillis() - start);
        }
    }

}
