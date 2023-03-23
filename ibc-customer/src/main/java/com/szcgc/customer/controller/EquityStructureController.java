package com.szcgc.customer.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.web.IbcId;
import com.szcgc.comm.web.WebConstants;
import com.szcgc.customer.model.EquityStructure;
import com.szcgc.customer.service.EquityStructureService;
import com.szcgc.customer.util.ExcelUtil;
import com.szcgc.customer.util.ParamVerifyUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 股权结构
 *
 * @author chenjiaming
 * @date 2022-9-20 14:45:07
 */
@Slf4j
@Api(tags = "股权结构")
@RestController
@RequestMapping("equity-structure")
public class EquityStructureController {

    @Resource
    private EquityStructureService equityStructureService;

    @Operation(summary = "根据项目id以及客户id获取股权结构列表")
    @GetMapping(WebConstants.INDEX)
    public IbcResponse<List<EquityStructure>> list(@IbcId @ApiParam(hidden = true) int accountId,
                                                   @RequestParam @ApiParam(name = "项目id", required = true) Integer projectId,
                                                   @RequestParam @ApiParam(name = "客户id", required = true) Integer custId) {
        return IbcResponse.ok(equityStructureService.list(accountId, projectId, custId));
    }

    @Operation(summary = "新增股权结构数据")
    @PostMapping(WebConstants.INSERT)
    public IbcResponse<EquityStructure> add(@IbcId @ApiParam(hidden = true) int accountId,
                                            @RequestBody @ApiParam(name = "股权结构数据", required = true) EquityStructure equityStructure) {
        String errorTips = ParamVerifyUtil.verify(equityStructure);
        if (StrUtil.isNotBlank(errorTips)) {
            return IbcResponse.error500(errorTips);
        }
        equityStructure.setCreateBy(accountId);
        equityStructure.setUpdateBy(accountId);
        return IbcResponse.ok(equityStructureService.insert(equityStructure));
    }

    @Operation(summary = "修改股权结构数据")
    @PostMapping(WebConstants.UPDATE)
    public IbcResponse<EquityStructure> edit(@IbcId @ApiParam(hidden = true) int accountId,
                                             @RequestBody @ApiParam(name = "股权结构数据", required = true) EquityStructure equityStructure) {
        if (ObjectUtil.isEmpty(equityStructure.getId())) {
            return IbcResponse.error500("id不能为空");
        }

        String errorTips = ParamVerifyUtil.verify(equityStructure);
        if (StrUtil.isNotBlank(errorTips)) {
            return IbcResponse.error500(errorTips);
        }
        equityStructure.setUpdateBy(accountId);
        equityStructureService.update(equityStructure);
        return IbcResponse.ok();
    }

    @Operation(summary = "删除股权结构数据")
    @DeleteMapping(WebConstants.DELETE)
    public IbcResponse<EquityStructure> del(@ApiParam(name = "id", required = true) @RequestParam Integer id) {
        equityStructureService.delete(id);
        return IbcResponse.ok();
    }

    @Operation(summary = "根据id获取股权结构数据")
    @GetMapping(WebConstants.DETAIL)
    public IbcResponse<EquityStructure> details(@ApiParam(name = "id", required = true) @RequestParam Integer id) {
        return IbcResponse.ok(equityStructureService.find(id).orElse(null));
    }

    @Operation(summary = "导出股权结构空模板")
    @GetMapping("export-bank-model")
    public void exportBankModel(HttpServletResponse response) throws Exception {
        ExcelUtil.exportBankModel(response, EquityStructure.class);
    }

    @Operation(summary = "导入股权结构")
    @PostMapping("import")
    public IbcResponse<List<EquityStructure>> importData(@ApiParam(name = "导入文件", required = true) MultipartFile file) throws Exception {
        List<EquityStructure> data = equityStructureService.importData(file);
        return IbcResponse.ok(data);
    }

    @Operation(summary = "批量新增股权结构数据")
    @PostMapping(WebConstants.BATCH_INSERT)
    public IbcResponse batchInsert(@IbcId @ApiParam(hidden = true) int accountId,
                                   @RequestParam @ApiParam(name = "项目id", required = true) Integer projectId,
                                   @RequestParam @ApiParam(name = "客户id", required = true) Integer custId,
                                   @RequestBody @ApiParam(name = "股权结构", required = true) List<EquityStructure> equityStructures) {
        equityStructures.forEach(obj -> {
            obj.setProjectId(projectId);
            obj.setCustomerId(custId);
            obj.setUpdateBy(accountId);
            obj.setCreateBy(accountId);
        });
        equityStructureService.batchInsert(equityStructures);
        return IbcResponse.ok();
    }

}
