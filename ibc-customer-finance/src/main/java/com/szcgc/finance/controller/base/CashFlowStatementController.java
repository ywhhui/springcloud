package com.szcgc.finance.controller.base;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.web.IbcId;
import com.szcgc.comm.web.WebConstants;
import com.szcgc.finance.model.base.CashFlowStatement;
import com.szcgc.finance.service.base.CashFlowStatementService;
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
 * 现金流量表
 *
 * @author chenjiaming
 * @date 2022-9-26 08:57:08
 */
@Slf4j
@Api(tags = "现金流量表")
@RestController
@RequestMapping("cash-flow-statement")
public class CashFlowStatementController {

    @Resource
    private CashFlowStatementService cashFlowStatementService;

    @GetMapping("search")
    @Operation(summary = "根据客户名称获取有现金流量表数据的客户")
    public IbcResponse<List<FinanceCustomerVo>> search(@ApiParam("关键字") String keyword) {
        List<FinanceCustomerVo> list = cashFlowStatementService.search(keyword);
        return IbcResponse.ok(list);
    }

    @Operation(summary = "获取历史现金流量表数据列表")
    @GetMapping(WebConstants.INDEX)
    public IbcResponse<List<CashFlowStatement>> list(@ApiParam(name = "客户id", required = true) Integer custId,
                                                     @ApiParam(name = "项目id,不传则表示获取历史所有,传了则获取当前项目的") Integer projectId) {
        if (ObjectUtil.isNull(custId)) {
            return IbcResponse.error500("客户id不能为空");
        }

        return IbcResponse.ok(cashFlowStatementService.list(custId, projectId));
    }

    @Operation(summary = "导入历史现金流量表数据")
    @PostMapping("history-import")
    public IbcResponse historyInsert(@IbcId @ApiParam(hidden = true) int accountId,
                                     @RequestBody @ApiParam(name = "历史数据对象", required = true) HistoryImportVo historyImportVo) {
        String errorTips = ParamVerifyUtil.verify(historyImportVo);
        if (StrUtil.isNotBlank(errorTips)) {
            return IbcResponse.error500(errorTips);
        }

        cashFlowStatementService.historyInsert(accountId, historyImportVo.getIds(), historyImportVo.getProjectId(), historyImportVo.getCustomerId());
        return IbcResponse.ok();
    }

    @Operation(summary = "新增现金流量表数据")
    @PostMapping(WebConstants.INSERT)
    public IbcResponse<CashFlowStatement> add(@IbcId @ApiParam(hidden = true) int accountId,
                                                   @RequestBody @ApiParam(name = "现金流量表数据", required = true) CashFlowStatement cashFlowStatement) {
        String errorTips = ParamVerifyUtil.verify(cashFlowStatement);
        if (StrUtil.isNotBlank(errorTips)) {
            return IbcResponse.error500(errorTips);
        }

        // 按项目id,客户id以及日期查是否已存在数据
        CashFlowStatement existsCashFlowStatement = cashFlowStatementService.findByProjectIdAndCustomerIdAndDate(cashFlowStatement.getProjectId(),
                cashFlowStatement.getCustomerId(), cashFlowStatement.getDate());
        if (ObjectUtil.isNotNull(existsCashFlowStatement)) {
            // 已存在变为修改
            cashFlowStatement.setId(existsCashFlowStatement.getId());
        }
        cashFlowStatement.setCreateBy(accountId);
        cashFlowStatement.setUpdateBy(accountId);

        cashFlowStatementService.insert(cashFlowStatement);
        return IbcResponse.ok();
    }

    @Operation(summary = "批量保存现金流量表数据")
    @PostMapping(WebConstants.BATCH_INSERT)
    public IbcResponse<CashFlowStatement> saveBatch(@IbcId @ApiParam(hidden = true) int accountId,
                                               @RequestBody @ApiParam(name = "现金流量表数据", required = true) List<CashFlowStatement> cashFlowStatementList) {
        String errorTips = ParamVerifyUtil.verify(cashFlowStatementList);
        if (StrUtil.isNotBlank(errorTips)) {
            return IbcResponse.error500(errorTips);
        }

        cashFlowStatementList = cashFlowStatementService.calcValue(cashFlowStatementList);

        for(CashFlowStatement cashFlowStatement : cashFlowStatementList) {
            // 按项目id,客户id以及日期查是否已存在数据
            CashFlowStatement existsCashFlowStatement = cashFlowStatementService.findByProjectIdAndCustomerIdAndDate(cashFlowStatement.getProjectId(),
                    cashFlowStatement.getCustomerId(), cashFlowStatement.getDate());
            if (ObjectUtil.isNotNull(existsCashFlowStatement)) {
                // 已存在变为修改
                cashFlowStatement.setId(existsCashFlowStatement.getId());
            }
            cashFlowStatement.setCreateBy(accountId);
            cashFlowStatement.setUpdateBy(accountId);
        }
        cashFlowStatementService.batchInsert(cashFlowStatementList);
        return IbcResponse.ok();
    }

    @Operation(summary = "修改现金流量表数据")
    @PostMapping(WebConstants.UPDATE)
    public IbcResponse<CashFlowStatement> editBatch(@IbcId @ApiParam(hidden = true) int accountId,
                                                    @RequestBody @ApiParam(name = "现金流量表数据", required = true) CashFlowStatement cashFlowStatement) {

        String errorTips = ParamVerifyUtil.verify(cashFlowStatement);
        if (StrUtil.isNotBlank(errorTips)) {
            return IbcResponse.error500(errorTips);
        }
        if (ObjectUtil.isNull(cashFlowStatement.getId())) {
            return IbcResponse.error500("id不能为空");
        }

        cashFlowStatement.setUpdateBy(accountId);

        cashFlowStatementService.update(cashFlowStatement);
        return IbcResponse.ok();
    }

    @Operation(summary = "删除现金流量表数据")
    @DeleteMapping(WebConstants.DELETE)
    public IbcResponse<CashFlowStatement> del(@ApiParam(name = "id", required = true) @RequestParam Integer id) {
        cashFlowStatementService.delete(id);
        return IbcResponse.ok();
    }

    @Operation(summary = "根据id获取现金流量表数据")
    @GetMapping(WebConstants.DETAIL)
    public IbcResponse<CashFlowStatement> details(@ApiParam(name = "id", required = true) @RequestParam Integer id) {
        CashFlowStatement cashFlowStatementInfo = cashFlowStatementService.find(id).orElse(null);
        return IbcResponse.ok(cashFlowStatementInfo);
    }

    @Operation(summary = "根据客户id,项目id,年月获取现金流量表数据")
    @GetMapping("detail-info")
    public IbcResponse<CashFlowStatement> detailsByProjectIdAndCustIdAndDate(@ApiParam(name = "项目id", required = true) @RequestParam Integer projectId,
                                                                             @ApiParam(name = "客户id", required = true) @RequestParam Integer custId,
                                                                             @ApiParam(name = "年月", required = true) @RequestParam String date) {
        CashFlowStatement cashFlowStatementInfo = cashFlowStatementService.findByProjectIdAndCustomerIdAndDate(projectId, custId, date);
        return IbcResponse.ok(cashFlowStatementInfo);
    }

}
