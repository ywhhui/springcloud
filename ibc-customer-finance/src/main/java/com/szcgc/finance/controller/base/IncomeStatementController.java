package com.szcgc.finance.controller.base;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.web.IbcId;
import com.szcgc.comm.web.WebConstants;
import com.szcgc.finance.model.base.IncomeStatement;
import com.szcgc.finance.service.base.IncomeStatementService;
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
 * 损益表
 *
 * @author chenjiaming
 * @date 2022-9-26 08:57:08
 */
@Slf4j
@Api(tags = "损益表")
@RestController
@RequestMapping("income-statement")
public class IncomeStatementController {

    @Resource
    private IncomeStatementService incomeStatementService;

    @GetMapping("search")
    @Operation(summary = "根据客户名称获取有损益表数据的客户")
    public IbcResponse<List<FinanceCustomerVo>> search(@ApiParam("关键字") String keyword) {
        List<FinanceCustomerVo> list = incomeStatementService.search(keyword);
        return IbcResponse.ok(list);
    }

    @Operation(summary = "获取历史损益表数据列表")
    @GetMapping(WebConstants.INDEX)
    public IbcResponse<List<IncomeStatement>> list(@ApiParam(name = "客户id", required = true) Integer custId,
                                                   @ApiParam(name = "项目id,不传则表示获取历史所有,传了则获取当前项目的") Integer projectId) {
        if (ObjectUtil.isNull(custId)) {
            return IbcResponse.error500("客户id不能为空");
        }

        return IbcResponse.ok(incomeStatementService.list(custId, projectId));
    }

    @Operation(summary = "导入历史损益表数据")
    @PostMapping("history-import")
    public IbcResponse historyInsert(@IbcId @ApiParam(hidden = true) int accountId,
                                     @RequestBody @ApiParam(name = "历史数据对象", required = true) HistoryImportVo historyImportVo) {
        String errorTips = ParamVerifyUtil.verify(historyImportVo);
        if (StrUtil.isNotBlank(errorTips)) {
            return IbcResponse.error500(errorTips);
        }

        incomeStatementService.historyInsert(accountId, historyImportVo.getIds(), historyImportVo.getProjectId(), historyImportVo.getCustomerId());
        return IbcResponse.ok();
    }

    @Operation(summary = "新增损益表数据")
    @PostMapping(WebConstants.INSERT)
    public IbcResponse<IncomeStatement> add(@IbcId @ApiParam(hidden = true) int accountId,
                                                 @RequestBody @ApiParam(name = "损益表数据", required = true) IncomeStatement incomeStatement) {
        String errorTips = ParamVerifyUtil.verify(incomeStatement);
        if (StrUtil.isNotBlank(errorTips)) {
            return IbcResponse.error500(errorTips);
        }

        // 按项目id,客户id以及日期查是否已存在数据
        IncomeStatement existsIncomeStatement = incomeStatementService.findByProjectIdAndCustomerIdAndDate(incomeStatement.getProjectId(),
                incomeStatement.getCustomerId(), incomeStatement.getDate());
        if (ObjectUtil.isNotNull(existsIncomeStatement)) {
            // 已存在变为修改
            incomeStatement.setId(existsIncomeStatement.getId());
        }
        incomeStatement.setCreateBy(accountId);
        incomeStatement.setUpdateBy(accountId);

        incomeStatementService.insert(incomeStatement);
        return IbcResponse.ok();
    }

    @Operation(summary = "批量保存损益表数据")
    @PostMapping(WebConstants.BATCH_INSERT)
    public IbcResponse<IncomeStatement> saveBatch(@IbcId @ApiParam(hidden = true) int accountId,
                                                    @RequestBody @ApiParam(name = "损益表数据", required = true) List<IncomeStatement> incomeStatementList) {
        String errorTips = ParamVerifyUtil.verify(incomeStatementList);
        if (StrUtil.isNotBlank(errorTips)) {
            return IbcResponse.error500(errorTips);
        }

        incomeStatementList = incomeStatementService.calcValue(incomeStatementList);

        for(IncomeStatement incomeStatement : incomeStatementList) {
            // 按项目id,客户id以及日期查是否已存在数据
            IncomeStatement existsIncomeStatement = incomeStatementService.findByProjectIdAndCustomerIdAndDate(incomeStatement.getProjectId(),
                    incomeStatement.getCustomerId(), incomeStatement.getDate());
            if (ObjectUtil.isNotNull(existsIncomeStatement)) {
                // 已存在变为修改
                incomeStatement.setId(existsIncomeStatement.getId());
            }
            incomeStatement.setCreateBy(accountId);
            incomeStatement.setUpdateBy(accountId);
        }
        incomeStatementService.batchInsert(incomeStatementList);
        return IbcResponse.ok();
    }

    @Operation(summary = "修改损益表数据")
    @PostMapping(WebConstants.UPDATE)
    public IbcResponse<IncomeStatement> editBatch(@IbcId @ApiParam(hidden = true) int accountId,
                                                  @RequestBody @ApiParam(name = "损益表数据", required = true) IncomeStatement incomeStatement) {

        String errorTips = ParamVerifyUtil.verify(incomeStatement);
        if (StrUtil.isNotBlank(errorTips)) {
            return IbcResponse.error500(errorTips);
        }
        if (ObjectUtil.isNull(incomeStatement.getId())) {
            return IbcResponse.error500("id不能为空");
        }

        incomeStatement.setUpdateBy(accountId);

        incomeStatementService.update(incomeStatement);
        return IbcResponse.ok();
    }

    @Operation(summary = "删除损益表数据")
    @DeleteMapping(WebConstants.DELETE)
    public IbcResponse<IncomeStatement> del(@ApiParam(name = "id", required = true) @RequestParam Integer id) {
        incomeStatementService.delete(id);
        return IbcResponse.ok();
    }

    @Operation(summary = "根据id获取损益表数据")
    @GetMapping(WebConstants.DETAIL)
    public IbcResponse<IncomeStatement> details(@ApiParam(name = "id", required = true) @RequestParam Integer id) {
        IncomeStatement incomeStatementInfo = incomeStatementService.find(id).orElse(null);
        return IbcResponse.ok(incomeStatementInfo);
    }

    @Operation(summary = "根据客户id,项目id,年月获取损益表数据")
    @GetMapping("detail-info")
    public IbcResponse<IncomeStatement> detailsByProjectIdAndCustIdAndDate(@ApiParam(name = "项目id", required = true) @RequestParam Integer projectId,
                                                                           @ApiParam(name = "客户id", required = true) @RequestParam Integer custId,
                                                                           @ApiParam(name = "年月", required = true) @RequestParam String date) {
        IncomeStatement incomeStatementInfo = incomeStatementService.findByProjectIdAndCustomerIdAndDate(projectId, custId, date);
        return IbcResponse.ok(incomeStatementInfo);
    }

}
