package com.szcgc.project.controller;

import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.util.JsonUtils;
import com.szcgc.comm.web.IbcId;
import com.szcgc.comm.web.WebConstants;
import com.szcgc.project.model.ReleaseDetailInfo;
import com.szcgc.project.service.IReleaseDetailService;
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
 *待解保抵押物详情
 */
@Api(tags = "待解保抵押物详情")
@RestController
@RequestMapping("release-detail")
public class ReleaseDetailController {

    private static final Log logger = LogFactory.getLog(ReleaseDetailController.class);

    @Autowired
    IReleaseDetailService releaseDetailService;

    /**
     * 添加
     * @param detailInfo
     * @return
     */
    @Operation(summary = "新增", description = "新增")
    @PostMapping(WebConstants.INSERT)
    public IbcResponse<Integer> insert(@IbcId @Parameter(hidden = true) int accountId, @RequestBody ReleaseDetailInfo detailInfo) {
        logger.info("iReleaseDetailService insert param:"+ JsonUtils.toJSONString(detailInfo));
        detailInfo.setCreateAt(LocalDateTime.now());
        detailInfo.setCreateBy(accountId);
        ReleaseDetailInfo result = releaseDetailService.insert(detailInfo);
        return IbcResponse.ok(result.getId());
    }

    /**
     * 修改
     * @param detailInfo
     * @return
     */
    @Operation(summary = "修改", description = "修改")
    @PostMapping(WebConstants.UPDATE)
    public IbcResponse<Integer> update(@IbcId @Parameter(hidden = true) int accountId,@RequestBody ReleaseDetailInfo detailInfo) {
        logger.info("update入参 iReleaseDetailService param:"+JsonUtils.toJSONString(detailInfo));
        detailInfo.setUpdateAt(LocalDateTime.now());
        detailInfo.setUpdateBy(accountId);
        ReleaseDetailInfo result = releaseDetailService.update(detailInfo);
        return IbcResponse.ok(result.getId());
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Operation(summary = "删除", description = "删除")
    @PostMapping(WebConstants.DELETE)
    public IbcResponse delete(@RequestParam(name = "id") Integer id) {
        logger.info("iReleaseDetailService delete入参:"+id);
        releaseDetailService.delete(id);
        return IbcResponse.ok();
    }

    /**
     *  根据项目id获取待解保抵押物信息列表
     * @param projectId
     * @return
     */
    @Operation(summary = "根据项目id获取待解保抵押物信息列表", description = "根据项目id获取待解保抵押物信息列表")
    @GetMapping("/project")
    public IbcResponse<List<ReleaseDetailInfo>> findByProjectId(@RequestParam("projectId") Integer projectId) {
        logger.info("releaseDetailService findByProjectId param:"+projectId);
        List<ReleaseDetailInfo> result = releaseDetailService.findByProjectId(projectId);
        return IbcResponse.ok(result);
    }

    @Operation(summary = "详情", description = "详情")
    @GetMapping(WebConstants.DETAIL)
    public IbcResponse<ReleaseDetailInfo> getById(@RequestParam("id") int id) {
        Optional<ReleaseDetailInfo> detailInfo = releaseDetailService.find(id);
        if (detailInfo.isPresent()) {
            return IbcResponse.ok(detailInfo.get());
        }
        return IbcResponse.error500("id不存在");
    }

}
