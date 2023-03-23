package com.szcgc.customer.controller.manufacture;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.web.IbcId;
import com.szcgc.comm.web.WebConstants;
import com.szcgc.customer.model.manufacture.ReturnedMoney;
import com.szcgc.customer.service.manufacture.ReturnedMoneyService;
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
 * 回款核实
 *
 * @author chenjiaming
 * @date 2022-9-20 14:45:07
 */
@Slf4j
@Api(tags = "回款核实")
@RestController
@RequestMapping("returned-money")
public class ReturnedMoneyController {

    @Resource
    private ReturnedMoneyService returnedMoneyService;

    @Operation(summary = "根据项目id以及客户id获取回款核实列表")
    @GetMapping(WebConstants.INDEX)
    public IbcResponse<List<ReturnedMoney>> list(@RequestParam @ApiParam(name = "项目id", required = true) Integer projectId,
                                                 @RequestParam @ApiParam(name = "客户id", required = true) Integer custId) {
        return IbcResponse.ok(returnedMoneyService.list(projectId, custId));
    }

    @Operation(summary = "新增回款核实数据")
    @PostMapping(WebConstants.INSERT)
    public IbcResponse<ReturnedMoney> add(@IbcId @ApiParam(hidden = true) int accountId,
                                          @RequestBody @ApiParam(name = "回款核实数据", required = true) ReturnedMoney returnedMoney) {
        String errorTips = ParamVerifyUtil.verify(returnedMoney);
        if (StrUtil.isNotBlank(errorTips)) {
            return IbcResponse.error500(errorTips);
        }
        returnedMoney.setCreateBy(accountId);
        returnedMoney.setUpdateBy(accountId);
        return IbcResponse.ok(returnedMoneyService.insert(returnedMoney));
    }

    @Operation(summary = "修改回款核实数据")
    @PostMapping(WebConstants.UPDATE)
    public IbcResponse<ReturnedMoney> edit(@IbcId @ApiParam(hidden = true) int accountId,
                                           @RequestBody @ApiParam(name = "回款核实数据", required = true) ReturnedMoney returnedMoney) {
        if (ObjectUtil.isEmpty(returnedMoney.getId())) {
            return IbcResponse.error500("id不能为空");
        }

        String errorTips = ParamVerifyUtil.verify(returnedMoney);
        if (StrUtil.isNotBlank(errorTips)) {
            return IbcResponse.error500(errorTips);
        }
        returnedMoney.setUpdateBy(accountId);
        returnedMoneyService.update(returnedMoney);
        return IbcResponse.ok();
    }

    @Operation(summary = "删除回款核实数据")
    @DeleteMapping(WebConstants.DELETE)
    public IbcResponse<ReturnedMoney> del(@ApiParam(name = "id", required = true) @RequestParam Integer id) {
        returnedMoneyService.delete(id);
        return IbcResponse.ok();
    }

    @Operation(summary = "根据id获取回款核实数据")
    @GetMapping(WebConstants.DETAIL)
    public IbcResponse<ReturnedMoney> details(@ApiParam(name = "id", required = true) @RequestParam Integer id) {
        return IbcResponse.ok(returnedMoneyService.find(id).orElse(null));
    }

    @Operation(summary = "导出回款核实空模板")
    @GetMapping("export-bank-model")
    public void exportBankModel(HttpServletResponse response) throws Exception {
        ExcelUtil.exportBankModel(response, ReturnedMoney.class);
    }

    @Operation(summary = "导入回款核实")
    @PostMapping("import")
    public IbcResponse<List<ReturnedMoney>> importData(@ApiParam(name = "导入文件", required = true) MultipartFile file) throws Exception {
        List<ReturnedMoney> data = returnedMoneyService.importData(file);
        return IbcResponse.ok(data);
    }

    @Operation(summary = "批量新增回款核实数据")
    @PostMapping(WebConstants.BATCH_INSERT)
    public IbcResponse batchInsert(@IbcId @ApiParam(hidden = true) int accountId,
                                   @RequestParam @ApiParam(name = "项目id", required = true) Integer projectId,
                                   @RequestParam @ApiParam(name = "客户id", required = true) Integer custId,
                                   @RequestBody @ApiParam(name = "回款核实数据", required = true) List<ReturnedMoney> returnedMonies) {
        returnedMonies.forEach(obj -> {
            obj.setProjectId(projectId);
            obj.setCustomerId(custId);
            obj.setUpdateBy(accountId);
            obj.setCreateBy(accountId);
        });
        returnedMoneyService.batchInsert(returnedMonies);
        return IbcResponse.ok();
    }
}
