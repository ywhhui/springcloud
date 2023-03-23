package com.szcgc.cougua.controller;

import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.util.JsonUtils;
import com.szcgc.comm.web.IbcId;
import com.szcgc.comm.web.WebConstants;
import com.szcgc.cougua.model.IndividualDetailInfo;
import com.szcgc.cougua.service.IIndividualDetailService;
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
 * 个人保证详情
 * @Author liaohong
 * @create 2022/9/14 15:47
 */
@Api(tags = "个人保证详情")
@RestController
@RequestMapping("individual-detail")
public class IndividualDetailController {

    private static final Log logger = LogFactory.getLog(IndividualDetailController.class);

    @Autowired
    IIndividualDetailService iIndividualDetailService;

    /**
     * 添加个人保证详情
     * @param detailInfo
     * @return
     */
    @Operation(summary = "新增", description = "新增")
    @PostMapping(WebConstants.INSERT)
    public IbcResponse<Integer> insert(@IbcId @Parameter(hidden = true) int accountId, @RequestBody IndividualDetailInfo detailInfo) {
        logger.info("individualDetailInfo入参 insert param:"+ JsonUtils.toJSONString(detailInfo));
        detailInfo.setCreateAt(LocalDateTime.now());
        detailInfo.setCreateBy(accountId);
        IndividualDetailInfo result = iIndividualDetailService.insert(detailInfo);
        return IbcResponse.ok(result.getId());
    }

    /**
     * 修改个人保证详情
     * @param detailInfo
     * @return
     */
    @Operation(summary = "修改", description = "修改")
    @PostMapping(WebConstants.UPDATE)
    public IbcResponse<Integer> update(@IbcId @Parameter(hidden = true) int accountId,@RequestBody IndividualDetailInfo detailInfo) {
        logger.info("update入参 IndividualDetailInfo param:"+JsonUtils.toJSONString(detailInfo));
        detailInfo.setUpdateAt(LocalDateTime.now());
        detailInfo.setUpdateBy(accountId);
        IndividualDetailInfo result = iIndividualDetailService.update(detailInfo);
        return IbcResponse.ok(result.getId());
    }

    /**
     * 删除个人保证详情
     * @param id
     * @return
     */
    @Operation(summary = "删除", description = "删除")
    @PostMapping(WebConstants.DELETE)
    public IbcResponse delete(@RequestParam(name = "id") Integer id) {
        logger.info("individualDetailInfo delete入参:"+id);
        iIndividualDetailService.delete(id);
        return IbcResponse.ok();
    }

    /**
     *  根据项目id和个人保证id获取持有资产列表
     * @param projectId
     * @return
     */
    @Operation(summary = "根据项目id和个人保证id获取持有资产列表", description = "根据项目id和个人保证id获取持有资产列表")
    @GetMapping("/project")
    public IbcResponse<List<IndividualDetailInfo>> findByProjectIdAndIndividualId(@RequestParam("projectId") Integer projectId,@RequestParam("individualId") Integer individualId) {
        logger.info("individualDetailInfo findByProjectId param:"+projectId+individualId);
        List<IndividualDetailInfo> result = iIndividualDetailService.findByProjectIdAndIndividualId(projectId,individualId);
        return IbcResponse.ok(result);
    }

    @Operation(summary = "详情", description = "详情")
    @GetMapping(WebConstants.DETAIL)
    public IbcResponse<IndividualDetailInfo> getById(@RequestParam("id") int id) {
        Optional<IndividualDetailInfo> detailInfo = iIndividualDetailService.find(id);
        if (detailInfo.isPresent()) {
            return IbcResponse.ok(detailInfo.get());
        }
        return IbcResponse.error500("id不存在");
    }

}
