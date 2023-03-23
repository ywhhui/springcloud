package com.szcgc.finance.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.web.IbcId;
import com.szcgc.comm.web.WebConstants;
import com.szcgc.finance.dto.FinanceDto;
import com.szcgc.finance.model.CreditRating;
import com.szcgc.finance.model.analysis.FinanceAnalysis;
import com.szcgc.finance.service.FinanceInfoService;
import com.szcgc.finance.util.ExcelUtil;
import com.szcgc.finance.util.ParamVerifyUtil;
import com.szcgc.finance.vo.AnalysisVo;
import com.szcgc.finance.vo.CodeMapAndListVo;
import com.szcgc.finance.vo.CreditRatingVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 财务管理
 *
 * @author chenjiaming
 * @date 2022-9-23 15:04:24
 */
@Slf4j
@Api(tags = "财务管理")
@RestController
@RequestMapping("finance")
public class FinanceInfoController {

    private final List<String> title = Lists.newArrayList("编码", "科目(单位：人民币元)", "前年", "去年", "去年同期", "当期");

    @Resource
    private FinanceInfoService financeService;

    @Operation(summary = "导出财务模板")
    @GetMapping("export-bank-model")
    public void exportFinanceBankModel(HttpServletResponse response) throws Exception {
        ExcelUtil.exportFinanceBankModel(response, title, FinanceDto.class);
    }

    @Operation(summary = "数据导入")
    @PostMapping("import")
    public IbcResponse importData(@IbcId @ApiParam(hidden = true) int accountId,
                                  @RequestParam @ApiParam(name = "项目id", required = true) Integer projectId,
                                  @RequestParam @ApiParam(name = "客户id", required = true) Integer custId,
                                  @RequestParam @ApiParam(name = "导入文件", required = true) MultipartFile file) throws Exception {
        String errorTips = financeService.importData(projectId, custId, file, accountId, title);
        if (StrUtil.isNotBlank(errorTips)) {
            return IbcResponse.error500(errorTips);
        }
        return IbcResponse.ok();
    }

    @Operation(summary = "财务分析")
    @PostMapping("analysis")
    public IbcResponse analysis(@IbcId @ApiParam(hidden = true) int accountId,
                                @RequestBody AnalysisVo analysisVo) {
        if (ObjectUtil.isNull(analysisVo.getId1()) && ObjectUtil.isNull(analysisVo.getId2())
                && ObjectUtil.isNull(analysisVo.getId3()) && ObjectUtil.isNull(analysisVo.getId4())) {
            return IbcResponse.error500("id不能都为空");
        }
        financeService.analysis(analysisVo.getId1(), analysisVo.getId2(), analysisVo.getId3(), analysisVo.getId4(), accountId);
        return IbcResponse.ok();
    }

    @Operation(summary = "获取财务分析详情")
    @GetMapping(WebConstants.DETAIL)
    public IbcResponse<FinanceAnalysis> detail(@RequestParam @ApiParam(name = "项目id", required = true) Integer projectId,
                                               @RequestParam @ApiParam(name = "客户id", required = true) Integer custId) {
        FinanceAnalysis financeAnalysis = financeService.detail(projectId, custId);
        return IbcResponse.ok(financeAnalysis);
    }

    @Operation(summary = "导出财务分析")
    @GetMapping("export")
    public void export(HttpServletResponse response,
                       @RequestParam @ApiParam(name = "项目id", required = true) Integer projectId,
                       @RequestParam @ApiParam(name = "客户id", required = true) Integer custId) throws Exception {
        financeService.export(response, projectId, custId);
    }


    @Operation(summary = "导出数据到评审报告")
    @GetMapping("export-report")
    public IbcResponse exportReport(@IbcId @ApiParam(hidden = true) int accountId,
                                    @RequestParam @ApiParam(name = "项目id", required = true) Integer projectId,
                                    @RequestParam @ApiParam(name = "客户id", required = true) Integer custId) throws Exception {

        String path = "template/普通类评审报告模板.docx";

        financeService.exportReport(accountId, projectId, custId, path);
        return IbcResponse.ok();
    }

    @Operation(summary = "财务表格字段说明")
    @GetMapping("financetable")
    public IbcResponse financetable() throws Exception {
        Map<String, CodeMapAndListVo> map = financeService.financetable();
        return IbcResponse.ok(map);
    }

    @Operation(summary = "获取财务分析简表以及定量分析数据")
    @GetMapping("finance-analysis")
    public IbcResponse<List<CreditRatingVo>> financeAnalysis(@RequestParam @ApiParam(name = "项目id", required = true) Integer projectId,
                                                             @RequestParam @ApiParam(name = "客户id", required = true) Integer custId) throws Exception {
        List<CreditRatingVo> list = financeService.financeAnalysis(projectId, custId);
        return IbcResponse.ok(list);
    }

    @Operation(summary = "获取资信评分数据")
    @GetMapping("qualitative")
    public IbcResponse<CreditRating> qualitative(@RequestParam @ApiParam(name = "项目id", required = true) Integer projectId,
                                                 @RequestParam @ApiParam(name = "客户id", required = true) Integer custId,
                                                 @RequestParam @ApiParam(name = "年月", required = true) String date) throws Exception {
        CreditRating creditRating = financeService.qualitative(projectId, custId, date);
        return IbcResponse.ok(creditRating);
    }

    @Operation(summary = "保存资信评分数据")
    @PostMapping("add-qualitative")
    public IbcResponse saveQualitative(@IbcId @ApiParam(hidden = true) int accountId, @RequestBody CreditRating creditRating) throws Exception {
        String errTip = ParamVerifyUtil.verify(creditRating);
        if (StrUtil.isNotBlank(errTip)) {
            return IbcResponse.error500(errTip);
        }
        creditRating.setCreateBy(accountId);
        creditRating.setUpdateBy(accountId);
        financeService.saveQualitative(creditRating);
        return IbcResponse.ok();
    }

}
