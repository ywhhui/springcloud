package com.szcgc.project.controller;

import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.message.IProjectSender;
import com.szcgc.comm.web.IbcId;
import com.szcgc.comm.web.WebConstants;
import com.szcgc.project.constant.ProjectActEnum;
import com.szcgc.project.model.InviteLoanInfo;
import com.szcgc.project.service.IInviteLoanService;
import com.szcgc.project.vo.InviteLoanOpinionVo;
import com.szcgc.project.vo.InviteLoanVo;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *请款流程
 */
@Api(tags = "请款流程")
@RestController
@RequestMapping("invite-loan")
public class InviteLoanController {

    @Autowired
    IInviteLoanService inviteLoanService;

    @Autowired
    IProjectSender sender;

    /**
     *  财务经理审批(请款) 提交接口
     *  启动登记出账结果子流程的条件：(业务待确定)1.如果是签发放款通知书 且 线下  2.分管领导审批（请款） 且 proApprAmt < 25000000   3.集团董事长审批（请款）且 proApprAmt >= 25000000
     */
    @Operation(summary = "财务经理审批提交", description = "财务经理审批提交")
    @PostMapping("/finance/submit")
    public IbcResponse<Integer> financeSubmit(@IbcId @Parameter(hidden = true) int accountId,@RequestBody InviteLoanVo inviteLoanVo) {
        inviteLoanService.submit(accountId, inviteLoanVo);
        //流程的接口
        sender.projectAction(accountId, inviteLoanVo.getProjectId(), ProjectActEnum.QK_Cwjlsp.name(), inviteLoanVo.getTaskId());
        return IbcResponse.ok();
    }

    /**
     *  补充请款资料(请款) 提交接口
     */
    @Operation(summary = "补充请款资料提交", description = "补充请款资料提交")
    @PostMapping("/manager/submit")
    public IbcResponse<Integer> managerSubmit(@IbcId @Parameter(hidden = true) int accountId,@RequestBody InviteLoanVo inviteLoanVo) {
        inviteLoanService.submit(accountId, inviteLoanVo);
        //流程的接口
        sender.projectAction(accountId, inviteLoanVo.getProjectId(), ProjectActEnum.QK_Bcqkzl.name(), inviteLoanVo.getTaskId());
        return IbcResponse.ok();
    }

    /**
     *  财务经理审批(请款) 提交接口
     */
    @Operation(summary = "财务经理审批提交(>=25000000)", description = "财务经理审批提交(>=25000000)")
    @PostMapping("/finances/submit")
    public IbcResponse<Integer> financesSubmit(@IbcId @Parameter(hidden = true) int accountId,@RequestBody InviteLoanVo inviteLoanVo) {
        inviteLoanService.submit(accountId, inviteLoanVo);
        //流程的接口
        sender.projectAction(accountId, inviteLoanVo.getProjectId(), ProjectActEnum.QK_Cwjlsps.name(), inviteLoanVo.getTaskId());
        return IbcResponse.ok();
    }

    /**
     *  财务负责人审批(请款) 提交接口
     */
    @Operation(summary = "财务负责人审批提交", description = "财务负责人审批提交")
    @PostMapping("/blamed/submit")
    public IbcResponse<Integer> blamedSubmit(@IbcId @Parameter(hidden = true) int accountId,@RequestBody InviteLoanVo inviteLoanVo) {
        inviteLoanService.submit(accountId, inviteLoanVo);
        //流程的接口
        sender.projectAction(accountId, inviteLoanVo.getProjectId(), ProjectActEnum.QK_Cwfzrsp.name(), inviteLoanVo.getTaskId());
        return IbcResponse.ok();
    }

    /**
     *  公司总经理审批(请款) 提交接口
     */
    @Operation(summary = "公司总经理审批提交", description = "公司总经理审批提交")
    @PostMapping("/managers/submit")
    public IbcResponse<Integer> managersSubmit(@IbcId @Parameter(hidden = true) int accountId,@RequestBody InviteLoanVo inviteLoanVo) {
        inviteLoanService.submit(accountId, inviteLoanVo);
        //流程的接口
        sender.projectAction(accountId, inviteLoanVo.getProjectId(), ProjectActEnum.QK_Gszjlsp.name(), inviteLoanVo.getTaskId());
        return IbcResponse.ok();
    }

    /**
     *  分管领导审批(请款) 提交接口
     */
    @Operation(summary = "分管领导审批提交", description = "分管领导审批提交")
    @PostMapping("/fgldsp/submit")
    public IbcResponse<Integer> fgldspSubmit(@IbcId @Parameter(hidden = true) int accountId,@RequestBody InviteLoanVo inviteLoanVo) {
        inviteLoanService.submit(accountId, inviteLoanVo);
        //流程的接口
        sender.projectAction(accountId, inviteLoanVo.getProjectId(), ProjectActEnum.QK_Fgldsp.name(), inviteLoanVo.getTaskId());
        return IbcResponse.ok();
    }

    /**
     *  财务分管领导(请款) 提交接口
     */
    @Operation(summary = "财务分管领导提交", description = "财务分管领导提交")
    @PostMapping("/cwfgld/submit")
    public IbcResponse<Integer> cwfgldSubmit(@IbcId @Parameter(hidden = true) int accountId,@RequestBody InviteLoanVo inviteLoanVo) {
        inviteLoanService.submit(accountId, inviteLoanVo);
        //流程的接口
        sender.projectAction(accountId, inviteLoanVo.getProjectId(), ProjectActEnum.QK_Cwfgld.name(), inviteLoanVo.getTaskId());
        return IbcResponse.ok();
    }

    /**
     *  财务总监审批(请款) 提交接口
     */
    @Operation(summary = "财务总监审批提交", description = "财务总监审批提交")
    @PostMapping("/cwzjsp/submit")
    public IbcResponse<Integer> cwzjspSubmit(@IbcId @Parameter(hidden = true) int accountId,@RequestBody InviteLoanVo inviteLoanVo) {
        inviteLoanService.submit(accountId, inviteLoanVo);
        //流程的接口
        sender.projectAction(accountId, inviteLoanVo.getProjectId(), ProjectActEnum.QK_Cwzjsp.name(), inviteLoanVo.getTaskId());
        return IbcResponse.ok();
    }

    /**
     *  集团总经理审批(请款) 提交接口
     */
    @Operation(summary = "集团总经理审批提交", description = "集团总经理审批提交")
    @PostMapping("/jtzjlsp/submit")
    public IbcResponse<Integer> jtzjlspSubmit(@IbcId @Parameter(hidden = true) int accountId,@RequestBody InviteLoanVo inviteLoanVo) {
        inviteLoanService.submit(accountId, inviteLoanVo);
        //流程的接口
        sender.projectAction(accountId, inviteLoanVo.getProjectId(), ProjectActEnum.QK_Jtzjlsp.name(), inviteLoanVo.getTaskId());
        return IbcResponse.ok();
    }

    /**
     *  集团董事长审批(请款) 提交接口
     */
    @Operation(summary = "集团董事长审批提交", description = "集团董事长审批提交")
    @PostMapping("/jtdszsp/submit")
    public IbcResponse<Integer> jtdszspSubmit(@IbcId @Parameter(hidden = true) int accountId,@RequestBody InviteLoanVo inviteLoanVo) {
        inviteLoanService.submit(accountId, inviteLoanVo);
        //流程的接口
        sender.projectAction(accountId, inviteLoanVo.getProjectId(), ProjectActEnum.QK_Jtdszsp.name(), inviteLoanVo.getTaskId());
        return IbcResponse.ok();
    }

    /**
     * 根据项目id 获取所有节点提交信息的列表
     * @param projectId
     * @return
     */
    @Operation(summary = "审批信息列表", description = "审批信息列表")
    @GetMapping("/project")
    public IbcResponse< List<InviteLoanOpinionVo>> findByProjectId(@RequestParam("projectId") Integer projectId) {
        List<InviteLoanOpinionVo> result = inviteLoanService.findByProjectId(projectId);
        return IbcResponse.ok(result);
    }

    /**
     * 根据项目id 获取所有节点提交信息的详情
     * @param projectId
     * @return
     */
    @Operation(summary = "详情", description = "详情")
    @GetMapping(WebConstants.DETAIL)
    public IbcResponse<InviteLoanInfo> findFirstByProjectIdOrderByIdDesc(@RequestParam("projectId") Integer projectId) {
        InviteLoanInfo result = inviteLoanService.findFirstByProjectIdOrderByIdDesc(projectId);
        return IbcResponse.ok(result);
    }

}
