package com.szcgc.project.controller;

import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.IbcResult;
import com.szcgc.comm.message.IProjectSender;
import com.szcgc.comm.util.SundryUtils;
import com.szcgc.comm.web.IbcId;
import com.szcgc.project.business.IbcProject;
import com.szcgc.project.constant.ProjectActEnum;
import com.szcgc.project.constant.ProjectBehaviour;
import com.szcgc.project.model.PreliminaryInfo;
import com.szcgc.project.vo.preliminary.GsBaseVo;
import com.szcgc.project.vo.preliminary.SupervisorInVo;
import com.szcgc.third.tyc.feign.ITycClient;
import com.szcgc.third.tyc.model.gs.GsBaseDto;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author liaohong
 * @create 2022/9/29 11:39
 */
@Api(tags = "项目受理")
@RestController
@RequestMapping("preliminary")
public class PreliminaryController {

    @Autowired
    IbcProject ibcProject;

    @Autowired
    IProjectSender sender;

    @Autowired
    ITycClient tycClient;

    @Operation(summary = "项目受理")
    @PostMapping("begin")
    public IbcResponse<String> begin(@IbcId @Parameter(hidden = true) int accountId,
                                     @RequestBody PreliminaryInfo preliminary) {
        IbcResult<String> rst = ibcProject.begin(accountId, preliminary);
        if (!rst.isOk())
            return IbcResponse.error400(rst.getValue());
        int projectId = SundryUtils.tryGetInt(rst.getValue(), 0);
        sender.projectBehaviour(accountId, projectId, ProjectBehaviour.preliminary.name());
        return IbcResponse.ok();
    }

    @Operation(summary = "分配项目角色")
    @PostMapping("assign")
    public IbcResponse<String> assign(@IbcId @Parameter(hidden = true) int accountId,
                                      @RequestBody SupervisorInVo vo) {
        if (SupervisorInVo.CATE_SELF == vo.getCate()) {
            ibcProject.assign(accountId, vo.getProjectId(), vo.getRoleAId(), vo.getRoleBId(), vo.getRoleCId());
            sender.projectAction(accountId, vo.getProjectId(), ProjectActEnum.PL_Assign.name(), vo.getTaskId());
        } else {
            ibcProject.assignClaim(accountId, vo.getTaskId(), vo.getDepartmentId());
        }
        return IbcResponse.ok();
    }

    @Operation(summary = "查询企业工商信息")
    @GetMapping("tyc")
    public IbcResponse<GsBaseVo> begin(@RequestParam String name) {
        GsBaseDto dto = tycClient.gs(name);
        if (dto == null)
            return IbcResponse.error400("未查到任何结果");
        GsBaseVo vo = new GsBaseVo();
        vo.creditCode = dto.creditCode;
        vo.legalPersonName = dto.legalPersonName;
        vo.phoneNumber = dto.phoneNumber;
        return IbcResponse.ok(vo);
    }
}
