package com.szcgc.cougua.controller;

import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.util.JsonUtils;
import com.szcgc.comm.web.IbcId;
import com.szcgc.comm.web.WebConstants;
import com.szcgc.cougua.model.CorporationInfo;
import com.szcgc.cougua.service.ICorporationService;
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
 * 企业保证
 * @Author liaohong
 * @create 2022/9/14 15:47
 */
@Api(tags = "企业保证")
@RestController
@RequestMapping("corporation")
public class CorporationController {

    private static final Log logger = LogFactory.getLog(CorporationController.class);

    @Autowired
    ICorporationService corporationService;

    /**
     * 添加企业保证
     * @param corporationInfo
     * @return
     */
    @Operation(summary = "新增", description = "新增")
    @PostMapping(WebConstants.INSERT)
    public IbcResponse<Integer> insert(@IbcId @Parameter(hidden = true) int accountId, @RequestBody CorporationInfo corporationInfo) {
        logger.info("入参 corporationInfo:"+ JsonUtils.toJSONString(corporationInfo));
        corporationInfo.setCreateAt(LocalDateTime.now());
        corporationInfo.setCreateBy(accountId);
        CorporationInfo result = corporationService.insert(corporationInfo);
        if (null == result) {
            return IbcResponse.error400("该项目已存在该企业！");
        }
        return IbcResponse.ok(result.getId());
    }

    /**
     * 删除企业保证
     * @param id
     * @return
     */
    @Operation(summary = "删除企业保证", description = "删除企业保证")
    @PostMapping(WebConstants.DELETE)
    public IbcResponse delete(@RequestParam(name = "id") Integer id) {
        logger.info("delete入参 corporationInfo:"+id);
        corporationService.delete(id);
        return IbcResponse.ok();
    }

    /**
     *  根据项目id获取项目的企业保证列表
     * @param projectId
     * @return
     */
    @Operation(summary = "根据项目id获取项目的企业保证列表", description = "根据项目id获取项目的企业保证列表")
    @GetMapping("/project")
    public IbcResponse<List<CorporationInfo>> findByProjectId(@RequestParam("projectId") Integer projectId) {
        logger.info("corporation findByProjectId param:"+projectId);
        List<CorporationInfo> result = corporationService.findByProjectId(projectId);
        return IbcResponse.ok(result);
    }

    @Operation(summary = "详情", description = "详情")
    @GetMapping(WebConstants.DETAIL)
    public IbcResponse<CorporationInfo> getById(@RequestParam("id") int id) {
        Optional<CorporationInfo> corporationInfo = corporationService.find(id);
        if (corporationInfo.isPresent()) {
            return IbcResponse.ok(corporationInfo.get());
        }
        return IbcResponse.error500("id不存在");
    }


}
