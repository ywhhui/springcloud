package com.szcgc.customer.controller.manufacture;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.web.IbcId;
import com.szcgc.comm.web.WebConstants;
import com.szcgc.customer.model.manufacture.SalesRevenue;
import com.szcgc.customer.service.manufacture.SalesRevenueService;
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
 * 销售收入
 *
 * @author chenjiaming
 * @date 2022-9-20 14:45:07
 */
@Slf4j
@Api(tags = "销售收入")
@RestController
@RequestMapping("sales-revenue")
public class SalesRevenueController {

    @Resource
    private SalesRevenueService salesRevenueService;

    @Operation(summary = "根据项目id以及客户id获取销售收入列表")
    @GetMapping(WebConstants.INDEX)
    public IbcResponse<List<SalesRevenue>> list(@RequestParam @ApiParam(name = "项目id", required = true) Integer projectId,
                                                @RequestParam @ApiParam(name = "客户id", required = true) Integer custId) {
        return IbcResponse.ok(salesRevenueService.list(projectId, custId));
    }

    @Operation(summary = "新增销售收入数据")
    @PostMapping(WebConstants.INSERT)
    public IbcResponse<SalesRevenue> add(@IbcId @ApiParam(hidden = true) int accountId,
                                         @RequestBody @ApiParam(name = "销售收入数据", required = true) SalesRevenue salesRevenue) {
        String errorTips = ParamVerifyUtil.verify(salesRevenue);
        if (StrUtil.isNotBlank(errorTips)) {
            return IbcResponse.error500(errorTips);
        }
        salesRevenue.setCreateBy(accountId);
        salesRevenue.setUpdateBy(accountId);
        return IbcResponse.ok(salesRevenueService.insert(salesRevenue));
    }

    @Operation(summary = "修改销售收入数据")
    @PostMapping(WebConstants.UPDATE)
    public IbcResponse<SalesRevenue> edit(@IbcId @ApiParam(hidden = true) int accountId,
                                          @RequestBody @ApiParam(name = "销售收入数据", required = true) SalesRevenue salesRevenue) {
        if (ObjectUtil.isEmpty(salesRevenue.getId())) {
            return IbcResponse.error500("id不能为空");
        }

        String errorTips = ParamVerifyUtil.verify(salesRevenue);
        if (StrUtil.isNotBlank(errorTips)) {
            return IbcResponse.error500(errorTips);
        }
        salesRevenue.setUpdateBy(accountId);
        salesRevenueService.update(salesRevenue);
        return IbcResponse.ok();
    }

    @Operation(summary = "删除销售收入数据")
    @DeleteMapping(WebConstants.DELETE)
    public IbcResponse<SalesRevenue> del(@ApiParam(name = "id", required = true) @RequestParam Integer id) {
        salesRevenueService.delete(id);
        return IbcResponse.ok();
    }

    @Operation(summary = "根据id获取销售收入数据")
    @GetMapping(WebConstants.DETAIL)
    public IbcResponse<SalesRevenue> details(@ApiParam(name = "id", required = true) @RequestParam Integer id) {
        return IbcResponse.ok(salesRevenueService.find(id).orElse(null));
    }

    @Operation(summary = "导出销售收入空模板")
    @GetMapping("export-bank-model")
    public void exportBankModel(HttpServletResponse response) throws Exception {
        ExcelUtil.exportBankModel(response, SalesRevenue.class);
    }

    @Operation(summary = "导入销售收入")
    @PostMapping("import")
    public IbcResponse<List<SalesRevenue> > importData(@ApiParam(name = "导入文件", required = true) MultipartFile file) throws Exception {
        List<SalesRevenue> data = salesRevenueService.importData(file);
        return IbcResponse.ok(data);
    }

    @Operation(summary = "批量新增销售收入数据")
    @PostMapping(WebConstants.BATCH_INSERT)
    public IbcResponse batchInsert(@IbcId @ApiParam(hidden = true) int accountId,
                                   @RequestParam @ApiParam(name = "项目id", required = true) Integer projectId,
                                   @RequestParam @ApiParam(name = "客户id", required = true) Integer custId,
                                   @RequestBody @ApiParam(name = "销售收入数据", required = true) List<SalesRevenue> salesRevenues) {
        salesRevenues.forEach(obj -> {
            obj.setProjectId(projectId);
            obj.setCustomerId(custId);
            obj.setUpdateBy(accountId);
            obj.setCreateBy(accountId);
        });
        salesRevenueService.batchInsert(salesRevenues);
        return IbcResponse.ok();
    }
}
