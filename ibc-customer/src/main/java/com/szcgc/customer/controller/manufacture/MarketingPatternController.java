package com.szcgc.customer.controller.manufacture;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.util.UsccUtil;
import com.szcgc.comm.web.IbcId;
import com.szcgc.comm.web.WebConstants;
import com.szcgc.customer.model.manufacture.MarketingPattern;
import com.szcgc.customer.service.manufacture.MarketingPatternService;
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
 * 营销模式
 *
 * @author chenjiaming
 * @date 2022-9-22 16:05:00
 */
@Slf4j
@Api(tags = "营销模式")
@RestController
@RequestMapping("marketing-pattern")
public class MarketingPatternController {

    @Resource
    private MarketingPatternService marketingPatternService;

    @Operation(summary = "根据项目id以及客户id获取营销模式列表")
    @GetMapping(WebConstants.INDEX)
    public IbcResponse<List<MarketingPattern>> list(@RequestParam @ApiParam(name = "项目id", required = true) Integer projectId,
                                                    @RequestParam @ApiParam(name = "客户id", required = true) Integer custId) {
        return IbcResponse.ok(marketingPatternService.list(projectId, custId));
    }

    @Operation(summary = "新增营销模式数据")
    @PostMapping(WebConstants.INSERT)
    public IbcResponse<MarketingPattern> add(@IbcId @ApiParam(hidden = true) int accountId,
                                             @RequestBody @ApiParam(name = "营销模式数据", required = true) MarketingPattern marketingPattern) {
        String errorTips = ParamVerifyUtil.verify(marketingPattern);
        if (StrUtil.isNotBlank(errorTips)) {
            return IbcResponse.error500(errorTips);
        }
        errorTips = UsccUtil.checkUscc(marketingPattern.getUniqueNo());

        if (StrUtil.isNotBlank(errorTips)) {
            IbcResponse.error500(errorTips);
        }
        marketingPattern.setCreateBy(accountId);
        marketingPattern.setUpdateBy(accountId);
        return IbcResponse.ok(marketingPatternService.insert(marketingPattern));
    }

    @Operation(summary = "修改营销模式数据")
    @PostMapping(WebConstants.UPDATE)
    public IbcResponse<MarketingPattern> edit(@IbcId @ApiParam(hidden = true) int accountId,
                                              @RequestBody @ApiParam(name = "营销模式数据", required = true) MarketingPattern marketingPattern) {
        if (ObjectUtil.isEmpty(marketingPattern.getId())) {
            return IbcResponse.error500("id不能为空");
        }

        String errorTips = ParamVerifyUtil.verify(marketingPattern);
        if (StrUtil.isNotBlank(errorTips)) {
            return IbcResponse.error500(errorTips);
        }
        errorTips = UsccUtil.checkUscc(marketingPattern.getUniqueNo());

        if (StrUtil.isNotBlank(errorTips)) {
            IbcResponse.error500(errorTips);
        }
        marketingPattern.setUpdateBy(accountId);
        marketingPatternService.update(marketingPattern);
        return IbcResponse.ok();
    }

    @Operation(summary = "删除营销模式数据")
    @DeleteMapping(WebConstants.DELETE)
    public IbcResponse<MarketingPattern> del(@RequestParam @ApiParam(name = "id", required = true) Integer id) {
        marketingPatternService.delete(id);
        return IbcResponse.ok();
    }

    @Operation(summary = "根据id获取营销模式数据")
    @GetMapping(WebConstants.DETAIL)
    public IbcResponse<MarketingPattern> details(@RequestParam @ApiParam(name = "id", required = true) Integer id) {
        return IbcResponse.ok(marketingPatternService.find(id).orElse(null));
    }

    @Operation(summary = "导出营销模式空模板")
    @GetMapping("export-bank-model")
    public void exportBankModel(HttpServletResponse response) throws Exception {
        ExcelUtil.exportBankModel(response, MarketingPattern.class);
    }

    @Operation(summary = "导入营销模式")
    @PostMapping("import")
    public IbcResponse<List<MarketingPattern>> importData(@ApiParam(name = "导入文件", required = true) MultipartFile file) throws Exception {
        List<MarketingPattern> data = marketingPatternService.importData(file);
        return IbcResponse.ok(data);
    }

    @Operation(summary = "批量新增营销模式数据")
    @PostMapping(WebConstants.BATCH_INSERT)
    public IbcResponse batchInsert(@IbcId @ApiParam(hidden = true) int accountId,
                                   @RequestParam @ApiParam(name = "项目id", required = true) Integer projectId,
                                   @RequestParam @ApiParam(name = "客户id", required = true) Integer custId,
                                   @RequestBody @ApiParam(name = "营销模式数据", required = true) List<MarketingPattern> marketingPatterns) {
        marketingPatterns.forEach(obj -> {
            obj.setProjectId(projectId);
            obj.setCustomerId(custId);
            obj.setUpdateBy(accountId);
            obj.setCreateBy(accountId);
        });
        marketingPatternService.batchInsert(marketingPatterns);
        return IbcResponse.ok();
    }

}
