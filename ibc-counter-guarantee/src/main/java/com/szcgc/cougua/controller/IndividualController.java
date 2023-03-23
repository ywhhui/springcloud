package com.szcgc.cougua.controller;

import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.util.JsonUtils;
import com.szcgc.comm.web.IbcId;
import com.szcgc.comm.web.WebConstants;
import com.szcgc.cougua.model.IndividualInfo;
import com.szcgc.cougua.service.IIndividualService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 个人保证
 * @Author liaohong
 * @create 2022/9/14 15:47
 */
@Api(tags = "个人保证")
@RestController
@RequestMapping("individual")
public class IndividualController {

    private static final Log logger = LogFactory.getLog(IndividualController.class);

    @Autowired
    IIndividualService individualService;

    /**
     * 添加个人保证
     * @param individualInfo
     * @return
     */
    @Operation(summary = "新增", description = "新增")
    @PostMapping(WebConstants.INSERT)
    public IbcResponse<Integer> insert(@IbcId @Parameter(hidden = true) int accountId, @RequestBody IndividualInfo individualInfo) {
        logger.info("入参 individualInfo param:"+ JsonUtils.toJSONString(individualInfo));
        individualInfo.setCreateAt(LocalDateTime.now());
        individualInfo.setCreateBy(accountId);
        IndividualInfo result = individualService.insert(individualInfo);
        return IbcResponse.ok(result.getId());
    }

    /**
     * 修改个人保证
     * @param individualInfo
     * @return
     */
    @Operation(summary = "修改", description = "修改")
    @PostMapping(WebConstants.UPDATE)
    public IbcResponse<Integer> update(@IbcId @Parameter(hidden = true) int accountId,@RequestBody IndividualInfo individualInfo) {
        logger.info("update入参 individualInfo param:"+JsonUtils.toJSONString(individualInfo));
        individualInfo.setUpdateAt(LocalDateTime.now());
        individualInfo.setUpdateBy(accountId);
        IndividualInfo result = individualService.update(individualInfo);
        return IbcResponse.ok(result.getId());
    }

    /**
     * 删除个人保证
     * @param id
     * @return
     */
    @Operation(summary = "删除", description = "删除")
    @PostMapping(WebConstants.DELETE)
    public IbcResponse delete(@RequestParam(name = "id") Integer id) {
        logger.info("delete入参 individualService:"+id);
        individualService.delete(id);
        return IbcResponse.ok();
    }

    /**
     *  根据项目id获取项目的个人保证
     * @param projectId
     * @return
     */
    @Operation(summary = "根据项目id获取项目的个人保证", description = "根据项目id获取项目的个人保证")
    @GetMapping("/project")
    public IbcResponse<List<IndividualInfo>> findByProjectId(@RequestParam("projectId") Integer projectId) {
        logger.info("individual入参 findByProjectId param:"+projectId);
        List<IndividualInfo> result = individualService.findByProjectId(projectId);
        return IbcResponse.ok(result);
    }

    @Operation(summary = "详情", description = "详情")
    @GetMapping(WebConstants.DETAIL)
    public IbcResponse<IndividualInfo> getById(@RequestParam("id") int id) {
        Optional<IndividualInfo> individualInfo = individualService.find(id);
        if (individualInfo.isPresent()) {
            return IbcResponse.ok(individualInfo.get());
        }
        return IbcResponse.error500("id不存在");
    }

}
