package com.szcgc.customer.controller.manufacture;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.web.IbcId;
import com.szcgc.comm.web.WebConstants;
import com.szcgc.customer.model.manufacture.ProductStructure;
import com.szcgc.customer.service.manufacture.ProductStructureService;
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
 * 产品结构
 *
 * @author chenjiaming
 * @date 2022-9-20 14:45:07
 */
@Slf4j
@Api(tags = "产品结构")
@RestController
@RequestMapping("product-structure")
public class ProductStructureController {

    @Resource
    private ProductStructureService productStructureService;

    @Operation(summary = "根据项目id以及客户id获取产品结构列表")
    @GetMapping(WebConstants.INDEX)
    public IbcResponse<List<ProductStructure>> list(@RequestParam @ApiParam(name = "项目id", required = true) Integer projectId,
                                                    @RequestParam @ApiParam(name = "客户id", required = true) Integer custId) {
        return IbcResponse.ok(productStructureService.list(projectId, custId));
    }

    @Operation(summary = "新增产品结构表数据")
    @PostMapping(WebConstants.INSERT)
    public IbcResponse<ProductStructure> add(@IbcId @ApiParam(hidden = true) int accountId,
                                             @RequestBody @ApiParam(name = "产品结构表数据", required = true) ProductStructure productStructure) {
        String errorTips = ParamVerifyUtil.verify(productStructure);
        if (StrUtil.isNotBlank(errorTips)) {
            return IbcResponse.error500(errorTips);
        }
        productStructure.setCreateBy(accountId);
        productStructure.setUpdateBy(accountId);
        return IbcResponse.ok(productStructureService.insert(productStructure));
    }

    @Operation(summary = "修改产品结构表数据")
    @PostMapping(WebConstants.UPDATE)
    public IbcResponse<ProductStructure> edit(@IbcId @ApiParam(hidden = true) int accountId,
                                              @RequestBody @ApiParam(name = "产品结构表数据", required = true) ProductStructure productStructure) {
        if (ObjectUtil.isEmpty(productStructure.getId())) {
            return IbcResponse.error500("id不能为空");
        }

        String errorTips = ParamVerifyUtil.verify(productStructure);
        if (StrUtil.isNotBlank(errorTips)) {
            return IbcResponse.error500(errorTips);
        }
        productStructure.setUpdateBy(accountId);
        productStructureService.update(productStructure);
        return IbcResponse.ok();
    }

    @Operation(summary = "删除产品结构表数据")
    @DeleteMapping(WebConstants.DELETE)
    public IbcResponse<ProductStructure> del(@ApiParam(name = "id", required = true) @RequestParam Integer id) {
        productStructureService.delete(id);
        return IbcResponse.ok();
    }

    @Operation(summary = "根据id获取产品结构表数据")
    @GetMapping(WebConstants.DETAIL)
    public IbcResponse<ProductStructure> details(@ApiParam(name = "id", required = true) @RequestParam Integer id) {
        return IbcResponse.ok(productStructureService.find(id).orElse(null));
    }

    @Operation(summary = "导出产品结构表空模板")
    @GetMapping("export-bank-model")
    public void exportBankModel(HttpServletResponse response) throws Exception {
        ExcelUtil.exportBankModel(response, ProductStructure.class);
    }

    @Operation(summary = "导入产品结构表")
    @PostMapping("import")
    public IbcResponse<List<ProductStructure>> importData(@ApiParam(name = "导入文件", required = true) MultipartFile file) throws Exception {
        List<ProductStructure> data = productStructureService.importData(file);
        return IbcResponse.ok(data);
    }

    @Operation(summary = "批量新增产品结构表数据")
    @PostMapping(WebConstants.BATCH_INSERT)
    public IbcResponse batchInsert(@IbcId @ApiParam(hidden = true) int accountId,
                                   @RequestParam @ApiParam(name = "项目id", required = true) Integer projectId,
                                   @RequestParam @ApiParam(name = "客户id", required = true) Integer custId,
                                   @RequestBody @ApiParam(name = "产品结构表数据", required = true) List<ProductStructure> productStructures) {
        productStructures.forEach(obj -> {
            obj.setProjectId(projectId);
            obj.setCustomerId(custId);
            obj.setUpdateBy(accountId);
            obj.setCreateBy(accountId);
        });
        productStructureService.batchInsert(productStructures);
        return IbcResponse.ok();
    }

}
