package com.szcgc.finance.controller;

import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.message.IProjectSender;
import com.szcgc.comm.web.IbcId;
import com.szcgc.comm.web.WebConstants;
import com.szcgc.finance.model.ExamineInfo;
import com.szcgc.finance.service.IExamineService;
import com.szcgc.finance.vo.PostAnalysisVo;
import com.szcgc.project.constant.ProjectActEnum;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 保后检查登记审核
 * @Author liaohong
 * @create 2020/10/21 10:22
 */
@Api(tags = "保后检查登记审核")
@RestController
@RequestMapping("examine")
public class ExamineController {

    @Autowired
    IExamineService examineService;

    @Autowired
    IProjectSender sender;

    @Operation(summary = "登记保后检查信息-信息录入")
    @PostMapping(WebConstants.INSERT)
    public IbcResponse<String> examineAdd(@IbcId @Parameter(hidden = true) int accountId, @RequestBody ExamineInfo info) {
        info.setEnrollAccountId(accountId);
        examineService.insert(info);
        return IbcResponse.ok();
    }

    @Operation(summary = "登记保后检查信息-信息编辑")
    @PostMapping(WebConstants.UPDATE)
    public IbcResponse<String> examineEdit(@IbcId @Parameter(hidden = true) int accountId,@RequestBody ExamineInfo param) {
        ExamineInfo info = examineService.find(param.getId()).get();
        info.setExamineDate(param.getExamineDate());
        info.setFinancialReport(param.getFinancialReport());
        info.setCtrlName(param.getCtrlName());
        info.setCtrlPhone(param.getCtrlPhone());
        info.setCfoName(param.getCfoName());
        info.setCfoPhone(param.getCfoPhone());
        info.setRiskLevel(param.getRiskLevel());
        info.setEmployCount(param.getEmployCount());
        info.setSalaryPayable(param.getSalaryPayable());
        info.setSalaryPayDate(param.getSalaryPayDate());
        info.setRentPayable(param.getRentPayable());
        info.setRentPayDate(param.getRentPayDate());
        info.setHydroPayable(param.getHydroPayable());
        info.setHydroPayDate(param.getHydroPayDate());
        info.setHasBadDebt(param.getHasBadDebt());
        info.setHasNewLawsuit(param.getHasNewLawsuit());
        info.setHasCheckCg(param.getHasCheckCg());
        info.setRemarks(param.getRemarks());
        info.setAuditComment(param.getAuditComment());
        examineService.update(info);
        return IbcResponse.ok();
    }

    @Operation(summary = "登记保后检查信息-信息详情")
    @GetMapping(WebConstants.DETAIL)
    public IbcResponse<ExamineInfo> examineDetail(@IbcId @Parameter(hidden = true) int accountId,
                                                  @RequestParam("examineId") int examineId) {
        ExamineInfo info = examineService.find(examineId).get();
        return IbcResponse.ok(info);
    }

    @Operation(summary = "登记保后检查信息-信息删除")
    @DeleteMapping(WebConstants.DELETE)
    public IbcResponse<String> examineDelete(@IbcId @Parameter(hidden = true) int accountId,
                                             @RequestParam("examineId") int examineId) {
        examineService.delete(examineId);
        return IbcResponse.ok();
    }

    @Operation(summary = "登记保后检查信息-信息列表")
    @GetMapping(WebConstants.INDEX)
    public IbcResponse<List<ExamineInfo>> examineList(@IbcId @Parameter(hidden = true) int accountId,
                                                      @RequestParam("projectId") int projectId) {
        List<ExamineInfo> infos = examineService.findByProjectId(projectId);
        return IbcResponse.ok(infos);
    }

    /**
     * 保后检查信息提交
     * @param accountId
     * @param projectId
     * @param taskId
     * @return
     */
    @Operation(summary = "保后检查信息登记提交")
    @PostMapping("/enroll/submit")
    public IbcResponse<String> examineEnroll(@IbcId @Parameter(hidden = true) int accountId,
                                             @RequestParam("projectId") int projectId,
                                             @RequestParam("taskId") String taskId) {
        examineService.enroll(projectId);
        sender.projectAction(accountId, projectId, ProjectActEnum.EX_Enroll.name(), taskId);
        return IbcResponse.ok();
    }

    @Operation(summary = "审核保后检查信息提交")
    @PostMapping("/audit/submit")
    public IbcResponse<String> examineAudit(@IbcId @Parameter(hidden = true) int accountId,
                                            @RequestParam("projectId") int projectId,
                                            @RequestParam("taskId") String taskId) {
        examineService.audit(projectId, accountId);
        sender.projectAction(accountId, projectId, ProjectActEnum.EX_Audit.name(), taskId);
        return IbcResponse.ok();
    }

    @Operation(summary = "登记保后检查信息-财务信息详情")
    @GetMapping("/finance" + WebConstants.DETAIL)
    public IbcResponse<List<PostAnalysisVo>> examineFinanceDetail(@RequestParam("projectId") int projectId,@RequestParam("financialReport") String financialReport) {
        List<PostAnalysisVo> postAnalysisVos = examineService.examineFinanceDetail(projectId,financialReport);
        return IbcResponse.ok(postAnalysisVos);
    }

    @Operation(summary = "登记保后检查信息-新增财务分析")
    @PostMapping("/finance" + WebConstants.INSERT)
    public IbcResponse examineFinanceAdd(@IbcId @Parameter(hidden = true) int accountId,@RequestParam("projectId") int projectId,@RequestParam("financialReport") String financialReport) {
        //生成保后检查分析数据 并写入
        examineService.examineFinanceAdd(projectId, financialReport, accountId);
        return IbcResponse.ok();
    }


}
