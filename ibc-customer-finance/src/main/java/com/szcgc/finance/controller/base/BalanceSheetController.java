package com.szcgc.finance.controller.base;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.web.IbcId;
import com.szcgc.comm.web.WebConstants;
import com.szcgc.finance.model.base.BalanceSheet;
import com.szcgc.finance.service.base.BalanceSheetService;
import com.szcgc.finance.util.ParamVerifyUtil;
import com.szcgc.finance.vo.FinanceCustomerVo;
import com.szcgc.finance.vo.HistoryImportVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 资产负债表
 *
 * @author chenjiaming
 * @date 2022-9-26 08:57:08
 */
@Slf4j
@Api(tags = "资产负债表")
@RestController
@RequestMapping("balance-sheet")
public class BalanceSheetController {

    @Resource
    private BalanceSheetService balanceSheetService;

    @GetMapping("search")
    @Operation(summary = "根据客户名称获取有资产负债表数据的客户")
    public IbcResponse<List<FinanceCustomerVo>> search(@ApiParam("关键字") String keyword) {
        List<FinanceCustomerVo> list = balanceSheetService.search(StrUtil.nullToDefault(keyword, ""));
        return IbcResponse.ok(list);
    }

    @Operation(summary = "获取资产负债表数据列表")
    @GetMapping(WebConstants.INDEX)
    public IbcResponse<List<BalanceSheet>> list(@ApiParam(name = "客户id", required = true) Integer custId,
                                                @ApiParam(name = "项目id,不传则表示获取历史所有,传了则获取当前项目的") Integer projectId) {
        if (ObjectUtil.isNull(custId)) {
            return IbcResponse.error500("客户id不能为空");
        }
        return IbcResponse.ok(balanceSheetService.list(custId, projectId));
    }

    @Operation(summary = "导入历史资产负债表数据")
    @PostMapping("history-import")
    public IbcResponse historyInsert(@IbcId @ApiParam(hidden = true) int accountId,
                                     @RequestBody @ApiParam(name = "历史数据对象", required = true) HistoryImportVo historyImportVo) {
        String errorTips = ParamVerifyUtil.verify(historyImportVo);
        if (StrUtil.isNotBlank(errorTips)) {
            return IbcResponse.error500(errorTips);
        }

        balanceSheetService.historyInsert(accountId, historyImportVo.getIds(), historyImportVo.getProjectId(), historyImportVo.getCustomerId());
        return IbcResponse.ok();
    }

    @Operation(summary = "新增资产负债表数据")
    @PostMapping(WebConstants.INSERT)
    public IbcResponse<BalanceSheet> add(@IbcId @ApiParam(hidden = true) int accountId,
                                              @RequestBody @ApiParam(name = "资产负债表数据", required = true) BalanceSheet balanceSheet) {
        String errorTips = ParamVerifyUtil.verify(balanceSheet);
        if (StrUtil.isNotBlank(errorTips)) {
            return IbcResponse.error500(errorTips);
        }

        // 按项目id,客户id以及日期查是否已存在数据
        BalanceSheet existsBalanceSheet = balanceSheetService.findByProjectIdAndCustomerIdAndDate(balanceSheet.getProjectId(),
                balanceSheet.getCustomerId(), balanceSheet.getDate());
        if (ObjectUtil.isNotNull(existsBalanceSheet)) {
            // 已存在变为修改
            balanceSheet.setId(existsBalanceSheet.getId());
        }
        balanceSheet.setCreateBy(accountId);
        balanceSheet.setUpdateBy(accountId);

        balanceSheetService.insert(balanceSheet);
        return IbcResponse.ok();
    }

    @Operation(summary = "批量保存资产负债表数据")
    @PostMapping(WebConstants.BATCH_INSERT)
    public IbcResponse<BalanceSheet> saveBatch(@IbcId @ApiParam(hidden = true) int accountId,
                                              @RequestBody @ApiParam(name = "资产负债表数据", required = true) List<BalanceSheet> balanceSheetList) {
        String errorTips = ParamVerifyUtil.verify(balanceSheetList);
        if (StrUtil.isNotBlank(errorTips)) {
            return IbcResponse.error500(errorTips);
        }

        balanceSheetList = balanceSheetService.calcValue(balanceSheetList);

        for(BalanceSheet balanceSheet : balanceSheetList) {
            // 按项目id,客户id以及日期查是否已存在数据
            BalanceSheet existsBalanceSheet = balanceSheetService.findByProjectIdAndCustomerIdAndDate(balanceSheet.getProjectId(),
                    balanceSheet.getCustomerId(), balanceSheet.getDate());
            if (ObjectUtil.isNotNull(existsBalanceSheet)) {
                // 已存在变为修改
                balanceSheet.setId(existsBalanceSheet.getId());
            }
            balanceSheet.setCreateBy(accountId);
            balanceSheet.setUpdateBy(accountId);
        }
        balanceSheetService.batchInsert(balanceSheetList);
        return IbcResponse.ok();
    }

    @Operation(summary = "修改资产负债表数据")
    @PostMapping(WebConstants.UPDATE)
    public IbcResponse<BalanceSheet> editBatch(@IbcId @ApiParam(hidden = true) int accountId,
                                               @RequestBody @ApiParam(name = "资产负债表数据", required = true) BalanceSheet balanceSheet) {

        String errorTips = ParamVerifyUtil.verify(balanceSheet);
        if (StrUtil.isNotBlank(errorTips)) {
            return IbcResponse.error500(errorTips);
        }
        if (ObjectUtil.isNull(balanceSheet.getId())) {
            return IbcResponse.error500("id不能为空");
        }

        balanceSheet.setUpdateBy(accountId);

        balanceSheetService.update(balanceSheet);
        return IbcResponse.ok();
    }

    @Operation(summary = "删除资产负债表数据")
    @DeleteMapping(WebConstants.DELETE)
    public IbcResponse<BalanceSheet> del(@ApiParam(name = "id", required = true) @RequestParam Integer id) {
        balanceSheetService.delete(id);
        return IbcResponse.ok();
    }

    @Operation(summary = "根据id获取资产负债表数据")
    @GetMapping(WebConstants.DETAIL)
    public IbcResponse<BalanceSheet> details(@ApiParam(name = "id", required = true) @RequestParam Integer id) {
        BalanceSheet balanceSheetInfo = balanceSheetService.find(id).orElse(null);
        return IbcResponse.ok(balanceSheetInfo);
    }

    @Operation(summary = "根据客户id,项目id,年月获取资产负债表数据")
    @GetMapping("detail-info")
    public IbcResponse<BalanceSheet> detailsByProjectIdAndCustIdAndDate(@ApiParam(name = "项目id", required = true) @RequestParam Integer projectId,
                                                                        @ApiParam(name = "客户id", required = true) @RequestParam Integer custId,
                                                                        @ApiParam(name = "年月", required = true) @RequestParam String date) {
        BalanceSheet balanceSheetInfo = balanceSheetService.findByProjectIdAndCustomerIdAndDate(projectId, custId, date);
        return IbcResponse.ok(balanceSheetInfo);
    }

}
