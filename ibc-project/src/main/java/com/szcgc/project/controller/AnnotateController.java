package com.szcgc.project.controller;

import com.szcgc.account.feign.IAccountClient;
import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.message.IProjectSender;
import com.szcgc.comm.model.IbcTree;
import com.szcgc.comm.web.IbcId;
import com.szcgc.comm.web.WebConstants;
import com.szcgc.project.constant.ProjectActEnum;
import com.szcgc.project.constant.ProjectConfigDic;
import com.szcgc.project.constant.ProjectConstants;
import com.szcgc.project.model.AttendInfo;
import com.szcgc.project.model.CommAuditInfo;
import com.szcgc.project.service.IAttendService;
import com.szcgc.project.service.ICommAuditService;
import com.szcgc.project.vo.CommAuditInVo;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * @Author liaohong
 * @create 2020/10/14 19:38
 */
@Api(tags = "项目评审-签批")
@RestController
@RequestMapping("eva-annotate")
public class AnnotateController {

    @Autowired
    ICommAuditService commAuditService;

    @Autowired
    IAttendService attendService;

    @Autowired
    IAccountClient accountClient;

    @Autowired
    IProjectSender sender;

    @Operation(summary = "查看已签批意见")
    @GetMapping("audit/" + WebConstants.INDEX)
    public IbcResponse<List<CommAuditInfo>> approval(@IbcId @Parameter(hidden = true) int accountId,
                                                     @RequestParam("projectId") int projectId) {
        List<CommAuditInfo> list = commAuditService.findByProjectId(projectId);
        if (list == null) {
            return IbcResponse.ok(Collections.EMPTY_LIST);
        }
        return IbcResponse.ok(list);
    }

    @Operation(summary = "风控签批可选项")
    @GetMapping("audit/risk/opinion")
    public IbcResponse<List<IbcTree>> attendOpinion(@IbcId @Parameter(hidden = true) int accountId, @RequestParam("projectId") int projectId) {
        //TODO 根据账号判断，不能根据金额，因为任务一旦被转办，金额还是不变
        AttendInfo attend = attendService.findLastByProjectId(projectId);
        List<IbcTree> list = attend.getAmount() > ProjectConstants.ANNOTATE_AMOUNT ? ProjectConfigDic.INSTANCE.annotateOpinionList : ProjectConfigDic.INSTANCE.annotateOpinionListFull;
        return IbcResponse.ok(list);
    }

    @Operation(summary = "部门负责人签批")
    @PostMapping("audit/leader" + WebConstants.INSERT)
    public IbcResponse<String> approvalLeader(@IbcId @Parameter(hidden = true) int accountId, @RequestBody CommAuditInVo vo) {
        addAudit(accountId, vo, ProjectActEnum.AT_Leader);
        return IbcResponse.ok();
    }

    @Operation(summary = "风控签批")
    @PostMapping("audit/risk" + WebConstants.INSERT)
    public IbcResponse<String> approvalRisk(@IbcId @Parameter(hidden = true) int accountId, @RequestBody CommAuditInVo vo) {
        //TODO 做转办处理
        addAudit(accountId, vo, ProjectActEnum.AT_Risk);
        return IbcResponse.ok();
    }

    @Operation(summary = "条线领导签批")
    @PostMapping("audit/boss" + WebConstants.INSERT)
    public IbcResponse<String> approvalBoss(@IbcId @Parameter(hidden = true) int accountId, @RequestBody CommAuditInVo vo) {
        addAudit(accountId, vo, ProjectActEnum.AT_Boss);
        return IbcResponse.ok();
    }

    private IbcResponse<String> addAudit(int accountId, CommAuditInVo vo, ProjectActEnum act) {
        CommAuditInfo info = new CommAuditInfo();
        info.setProjectId(vo.getProjectId());
        info.setOpinion(vo.getOpinion());
        info.setRemarks(vo.getRemarks());
        info.setAccountId(accountId);
        info.setAccountName(accountClient.findAccount(accountId).getRealName());
        info.setCreateAt(LocalDateTime.now());
        info.setCaseName(act.name());
        info.setCaseNameCn(act.getCaseName());
        commAuditService.insert(info);
        sender.projectAction(accountId, vo.getProjectId(), act.name(), vo.getOpinion(), vo.getRemarks(), vo.getTaskId(), String.valueOf(info.getId()));
        return IbcResponse.ok();
    }

}
