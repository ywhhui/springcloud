package com.szcgc.project.controller;

import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.util.JsonUtils;
import com.szcgc.comm.web.IbcId;
import com.szcgc.project.model.ReleaseInfo;
import com.szcgc.project.service.IReleaseService;
import com.szcgc.project.vo.ReleaseAuditVo;
import com.szcgc.project.vo.ReleaseRegisterVo;
import com.szcgc.project.vo.ReleaseSubmitVo;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *解保信息
 */
@Api(tags = "解保信息")
@RestController
@RequestMapping("release")
public class ReleaseController {

    private static final Log logger = LogFactory.getLog(ReleaseController.class);

    @Autowired
    IReleaseService releaseService;

    /**
     * 登记还款证明书审批提交
     */
    @Operation(summary = "登记提交", description = "登记提交")
    @PostMapping("/register/submit")
    public IbcResponse<Integer> registerSubmit(@IbcId @Parameter(hidden = true) int accountId,@RequestBody ReleaseRegisterVo releaseRegisterVo) {
        logger.info("releaseService registerSubmit param:"+ JsonUtils.toJSONString(releaseRegisterVo));
        return releaseService.registerSubmit(accountId, releaseRegisterVo);
    }

    /**
     * 根据项目id 登记还款证明书的 登记项目终止信息详情
     * @param projectId
     * @return
     */
    @Operation(summary = "详情", description = "详情")
    @GetMapping("/project")
    public IbcResponse<ReleaseInfo> getById(@RequestParam("projectId") Integer projectId) {
        ReleaseInfo result = releaseService.findFirstByProjectIdOrderByIdDesc(projectId);
        return IbcResponse.ok(result);
    }

    /**
     * 审核终止报告 的提交
     */
    @Operation(summary = "审核提交", description = "审核提交")
    @PostMapping("/audit/submit")
    public IbcResponse<Integer> auditSubmit(@IbcId @Parameter(hidden = true) int accountId,@RequestBody ReleaseAuditVo releaseAuditVo) {
        logger.info("releaseService auditSubmit param:"+ JsonUtils.toJSONString(releaseAuditVo));
        return releaseService.auditSubmit(accountId, releaseAuditVo);
    }

    /**
     * 解除反担保物 或者 退还保证金的 审批提交
     */
    @Operation(summary = "解除反担保物或者退还保证金的提交", description = "解除反担保物或者退还保证金的提交")
    @PostMapping("/guarantee/submit")
    public IbcResponse<Integer> guaranteeSubmit(@IbcId @Parameter(hidden = true) int accountId,@RequestBody ReleaseSubmitVo submitVo) {
        logger.info("releaseService guaranteeSubmit param:"+ JsonUtils.toJSONString(submitVo));
        return releaseService.guaranteeSubmit(accountId, submitVo);
    }


}
