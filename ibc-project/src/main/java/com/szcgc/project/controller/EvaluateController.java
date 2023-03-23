package com.szcgc.project.controller;

import com.szcgc.account.feign.IAccountClient;
import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.IbcResult;
import com.szcgc.comm.message.IProjectSender;
import com.szcgc.comm.model.IbcTree;
import com.szcgc.comm.util.StringUtils;
import com.szcgc.comm.web.IbcId;
import com.szcgc.comm.web.WebConstants;
import com.szcgc.file.constant.FileCateEnum;
import com.szcgc.file.feign.IFileClient;
import com.szcgc.file.model.FileInfo;
import com.szcgc.project.constant.ProjectActEnum;
import com.szcgc.project.constant.ProjectConfigDic;
import com.szcgc.project.model.*;
import com.szcgc.project.service.IAttendService;
import com.szcgc.project.service.ICommAuditService;
import com.szcgc.project.service.ICommentService;
import com.szcgc.project.vo.CommAuditInVo;
import com.szcgc.project.vo.evaluate.AttendInVo;
import com.szcgc.project.vo.evaluate.CommentInVo;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author liaohong
 * @create 2022/10/11 19:07
 */
@Api(tags = "项目评审")
@RestController
@RequestMapping("evaluate")
public class EvaluateController {

    @Autowired
    ICommentService commentService;

    @Autowired
    ICommAuditService commAuditService;

    @Autowired
    IAttendService attendService;

    //@Autowired
    //IProjectSupervisorService supervisorService;

    @Autowired
    IFileClient fileClient;

    @Autowired
    IAccountClient accountClient;

    @Autowired
    IProjectSender sender;

    /**
     * ************ B角意见 ************************************************
     */
    @Operation(summary = "提交B角意见")
    @PostMapping("comment" + WebConstants.INSERT)
    public IbcResponse<String> addComment(@IbcId @Parameter(hidden = true) int accountId, @RequestBody CommentInVo vo) {
        CommentInfo info = new CommentInfo();
        info.setProjectId(vo.getProjectId());
        info.setOpinion(vo.getOpinion());
        info.setRemarks(vo.getRemarks());
        info.setAccountId(accountId);
        info.setAccountName(accountClient.findAccount(accountId).getRealName());
        info.setCreateAt(LocalDateTime.now());
        if (!StringUtils.isEmpty(vo.getFilePath())) {
            com.szcgc.file.model.FileInfo file = new com.szcgc.file.model.FileInfo();
            file.setCreateBy(accountId);
            file.setCate(FileCateEnum.Bg_CommentB);
            file.setProjectId(vo.getProjectId());
            file.setPath(vo.getFilePath());
            IbcResult<Integer> rst = fileClient.add(file);
            if (rst.isOk()) {
                info.setFileId(rst.getValue());
            }
        }
        commentService.insert(info);
        sender.projectAction(accountId, vo.getProjectId(), ProjectActEnum.EV_Comment.name(), vo.getOpinion(), vo.getRemarks(), vo.getTaskId(), String.valueOf(info.getId()));
        return IbcResponse.ok();
    }

    @Operation(summary = "查看已提交B角意见")
    @GetMapping("comment" + WebConstants.INDEX)
    public IbcResponse<List<CommentInfo>> listComment(@IbcId @Parameter(hidden = true) int accountId, @RequestParam("projectId") int projectId) {
        List<CommentInfo> infos = commentService.findByProjectId(projectId);
        for (CommentInfo info : infos) {
            if (info.getFileId() > 0) {
                FileInfo file = fileClient.findById(info.getFileId());
                if (file != null) {
                    info.setFilePath(file.getPath());
                }
            }
        }
        return IbcResponse.ok(infos);
    }

    /**
     * ************ 部门预审意见意见 ************************************************
     */
    @Operation(summary = "部门预审")
    @PostMapping("audit/depart" + WebConstants.INSERT)
    public IbcResponse<String> addDepartAudit(@IbcId @Parameter(hidden = true) int accountId, @RequestBody CommAuditInVo vo) {
        CommAuditInfo info = new CommAuditInfo();
        info.setProjectId(vo.getProjectId());
        info.setOpinion(vo.getOpinion());
        info.setRemarks(vo.getRemarks());
        info.setAccountId(accountId);
        info.setAccountName(accountClient.findAccount(accountId).getRealName());
        info.setCreateAt(LocalDateTime.now());
        info.setCaseName(ProjectActEnum.EV_Department_Audit.name());
        info.setCaseNameCn(ProjectActEnum.EV_Department_Audit.getCaseName());
        commAuditService.insert(info);
        sender.projectAction(accountId, vo.getProjectId(), ProjectActEnum.EV_Department_Audit.name(), vo.getOpinion(), vo.getRemarks(), vo.getTaskId(), String.valueOf(info.getId()));
        return IbcResponse.ok();
    }

    /**
     * ************ 提交上会 ************************************************
     */
    @Operation(summary = "上会可选会议组别")
    @GetMapping("attend/opinion")
    public IbcResponse<List<IbcTree>> attendOpinion(@RequestParam("projectId") int projectId) {
        AttendInfo attend = attendService.findLastByProjectId(projectId);
        List<IbcTree> list = attend == null ? ProjectConfigDic.INSTANCE.meetingOpinionList_no_spec : ProjectConfigDic.INSTANCE.meetingOpinionList_no_one;
        return IbcResponse.ok(list);
    }

    @Operation(summary = "选择会议")
    @PostMapping("attend" + WebConstants.INSERT)
    public IbcResponse<String> addAttend(@IbcId @Parameter(hidden = true) int accountId, @RequestBody AttendInVo vo) {
        AttendInfo info = new AttendInfo();
        info.setAmount(vo.getAmount());
        info.setMeetingAt(vo.getMeetingAt());
        info.setProjectId(vo.getProjectId());
        info.setOpinion(vo.getOpinion());
        info.setRemarks(vo.getRemarks());
        info.setAccountId(accountId);
        info.setAccountName(accountClient.findAccount(accountId).getRealName());
        info.setCreateAt(LocalDateTime.now());
        attendService.insert(info);
        sender.projectAction(accountId, vo.getProjectId(), ProjectActEnum.EV_Attend.name(), vo.getOpinion(), vo.getRemarks(), vo.getTaskId(), String.valueOf(info.getId()));
        return IbcResponse.ok();
    }

}
