package com.szcgc.customer.controller.manufacture;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.web.IbcId;
import com.szcgc.comm.web.WebConstants;
import com.szcgc.customer.model.manufacture.UsageAmount;
import com.szcgc.customer.service.manufacture.UsageAmountService;
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
 * 水煤电用量核实
 *
 * @author chenjiaming
 * @date 2022-9-20 14:45:07
 */
@Slf4j
@Api(tags = "水煤电用量核实")
@RestController
@RequestMapping("usage-amount")
public class UsageAmountController {

    @Resource
    private UsageAmountService usageAmountService;

    @Operation(summary = "根据项目id以及客户id获取水煤电用量核实列表")
    @GetMapping(WebConstants.INDEX)
    public IbcResponse<List<UsageAmount>> list(@RequestParam @ApiParam(name = "项目id", required = true) Integer projectId,
                                               @RequestParam @ApiParam(name = "客户id", required = true) Integer custId) {
        return IbcResponse.ok(usageAmountService.list(projectId, custId));
    }

    @Operation(summary = "新增水煤电用量核实数据")
    @PostMapping(WebConstants.INSERT)
    public IbcResponse<UsageAmount> add(@IbcId @ApiParam(hidden = true) int accountId,
                                        @RequestBody @ApiParam(name = "水煤电用量核实数据", required = true) UsageAmount usageAmount) {
        String errorTips = ParamVerifyUtil.verify(usageAmount);
        if (StrUtil.isNotBlank(errorTips)) {
            return IbcResponse.error500(errorTips);
        }
        usageAmount.setCreateBy(accountId);
        usageAmount.setUpdateBy(accountId);
        return IbcResponse.ok(usageAmountService.insert(usageAmount));
    }

    @Operation(summary = "修改水煤电用量核实数据")
    @PostMapping(WebConstants.UPDATE)
    public IbcResponse<UsageAmount> edit(@IbcId @ApiParam(hidden = true) int accountId,
                                         @RequestBody @ApiParam(name = "水煤电用量核实数据", required = true) UsageAmount usageAmount) {
        if (ObjectUtil.isEmpty(usageAmount.getId())) {
            return IbcResponse.error500("id不能为空");
        }

        String errorTips = ParamVerifyUtil.verify(usageAmount);
        if (StrUtil.isNotBlank(errorTips)) {
            return IbcResponse.error500(errorTips);
        }
        usageAmount.setUpdateBy(accountId);
        usageAmountService.update(usageAmount);
        return IbcResponse.ok();
    }

    @Operation(summary = "删除水煤电用量核实数据")
    @DeleteMapping(WebConstants.DELETE)
    public IbcResponse<UsageAmount> del(@ApiParam(name = "id", required = true) @RequestParam Integer id) {
        usageAmountService.delete(id);
        return IbcResponse.ok();
    }

    @Operation(summary = "根据id获取水煤电用量核实数据")
    @GetMapping(WebConstants.DETAIL)
    public IbcResponse<UsageAmount> details(@ApiParam(name = "id", required = true) @RequestParam Integer id) {
        return IbcResponse.ok(usageAmountService.find(id).orElse(null));
    }

    @Operation(summary = "导出水煤电用量核实空模板")
    @GetMapping("export-bank-model")
    public void exportBankModel(HttpServletResponse response) throws Exception {
        ExcelUtil.exportBankModel(response, UsageAmount.class);
    }

    @Operation(summary = "导入水煤电用量核实")
    @PostMapping("import")
    public IbcResponse<List<UsageAmount>> importData(@ApiParam(name = "导入文件", required = true) MultipartFile file) throws Exception {
        List<UsageAmount> data = usageAmountService.importData(file);
        return IbcResponse.ok(data);
    }

    @Operation(summary = "批量新增水煤电用量核实数据")
    @PostMapping(WebConstants.BATCH_INSERT)
    public IbcResponse batchInsert(@IbcId @ApiParam(hidden = true) int accountId,
                                   @RequestParam @ApiParam(name = "项目id", required = true) Integer projectId,
                                   @RequestParam @ApiParam(name = "客户id", required = true) Integer custId,
                                   @RequestBody @ApiParam(name = "水煤电用量核实", required = true) List<UsageAmount> usageAmounts) {
        usageAmounts.forEach(obj -> {
            obj.setProjectId(projectId);
            obj.setCustomerId(custId);
            obj.setUpdateBy(accountId);
            obj.setCreateBy(accountId);
        });
        usageAmountService.batchInsert(usageAmounts);
        return IbcResponse.ok();
    }

}
