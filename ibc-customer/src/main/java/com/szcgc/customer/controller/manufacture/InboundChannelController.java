package com.szcgc.customer.controller.manufacture;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.util.UsccUtil;
import com.szcgc.comm.web.IbcId;
import com.szcgc.comm.web.WebConstants;
import com.szcgc.customer.constant.DicEnum;
import com.szcgc.customer.model.manufacture.InboundChannel;
import com.szcgc.customer.service.manufacture.InboundChannelService;
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
 * 进货渠道
 *
 * @author chenjiaming
 * @date 2022-9-20 14:45:07
 */
@Slf4j
@Api(tags = "进货渠道")
@RestController
@RequestMapping("inbound-channel")
public class InboundChannelController {

    @Resource
    private InboundChannelService inboundChannelService;

    @Operation(summary = "根据项目id以及客户id获取进货渠道列表")
    @GetMapping(WebConstants.INDEX)
    public IbcResponse<List<InboundChannel>> list(@RequestParam @ApiParam(name = "项目id", required = true) Integer projectId,
                                                  @RequestParam @ApiParam(name = "客户id", required = true) Integer custId) {
        return IbcResponse.ok(inboundChannelService.list(projectId, custId));
    }

    @Operation(summary = "新增进货渠道数据")
    @PostMapping(WebConstants.INSERT)
    public IbcResponse<InboundChannel> add(@IbcId @ApiParam(hidden = true) int accountId,
                                           @RequestBody @ApiParam(name = "进货渠道数据", required = true) InboundChannel inboundChannel) {
        String errorTips = ParamVerifyUtil.verify(inboundChannel);
        if (StrUtil.isNotBlank(errorTips)) {
            return IbcResponse.error500(errorTips);
        }
        if (DicEnum.CUST_TYPE_PERSON.getValue().intValue() == inboundChannel.getSupplierType()) {
            errorTips = UsccUtil.isIdentityCode(inboundChannel.getUniqueNo());
        } else {
            errorTips = UsccUtil.checkUscc(inboundChannel.getUniqueNo());
        }

        if (StrUtil.isNotBlank(errorTips)) {
            IbcResponse.error500(errorTips);
        }
        inboundChannel.setCreateBy(accountId);
        inboundChannel.setUpdateBy(accountId);
        return IbcResponse.ok(inboundChannelService.insert(inboundChannel));
    }

    @Operation(summary = "修改进货渠道数据")
    @PostMapping(WebConstants.UPDATE)
    public IbcResponse<InboundChannel> edit(@IbcId @ApiParam(hidden = true) int accountId,
                                            @RequestBody @ApiParam(name = "进货渠道数据", required = true) InboundChannel inboundChannel) {
        if (ObjectUtil.isEmpty(inboundChannel.getId())) {
            return IbcResponse.error500("id不能为空");
        }

        String errorTips = ParamVerifyUtil.verify(inboundChannel);
        if (StrUtil.isNotBlank(errorTips)) {
            return IbcResponse.error500(errorTips);
        }
        if (DicEnum.CUST_TYPE_PERSON.getValue().intValue() == inboundChannel.getSupplierType()) {
            errorTips = UsccUtil.isIdentityCode(inboundChannel.getUniqueNo());
        } else {
            errorTips = UsccUtil.checkUscc(inboundChannel.getUniqueNo());
        }

        if (StrUtil.isNotBlank(errorTips)) {
            IbcResponse.error500(errorTips);
        }

        inboundChannel.setUpdateBy(accountId);
        inboundChannelService.update(inboundChannel);
        return IbcResponse.ok();
    }

    @Operation(summary = "删除进货渠道数据")
    @DeleteMapping(WebConstants.DELETE)
    public IbcResponse<InboundChannel> del(@RequestParam @ApiParam(name = "id", required = true) Integer id) {
        inboundChannelService.delete(id);
        return IbcResponse.ok();
    }

    @Operation(summary = "根据id获取进货渠道数据")
    @GetMapping(WebConstants.DETAIL)
    public IbcResponse<InboundChannel> details(@RequestParam @ApiParam(name = "id", required = true) Integer id) {
        return IbcResponse.ok(inboundChannelService.find(id).orElse(null));
    }

    @Operation(summary = "导出进货渠道空模板")
    @GetMapping("export-bank-model")
    public void exportBankModel(HttpServletResponse response) throws Exception {
        ExcelUtil.exportBankModel(response, InboundChannel.class);
    }

    @Operation(summary = "导入进货渠道")
    @PostMapping("import")
    public IbcResponse<List<InboundChannel>> importData(@ApiParam(name = "导入文件", required = true) MultipartFile file) throws Exception {
        List<InboundChannel> data = inboundChannelService.importData(file);
        return IbcResponse.ok(data);
    }

    @Operation(summary = "批量新增进货渠道数据")
    @PostMapping(WebConstants.BATCH_INSERT)
    public IbcResponse batchInsert(@IbcId @ApiParam(hidden = true) int accountId,
                                   @RequestParam @ApiParam(name = "项目id", required = true) Integer projectId,
                                   @RequestParam @ApiParam(name = "客户id", required = true) Integer custId,
                                   @RequestBody @ApiParam(name = "进货渠道数据", required = true) List<InboundChannel> inboundChannel) {
        inboundChannel.forEach(obj -> {
            obj.setProjectId(projectId);
            obj.setCustomerId(custId);
            obj.setUpdateBy(accountId);
            obj.setCreateBy(accountId);
        });
        inboundChannelService.batchInsert(inboundChannel);
        return IbcResponse.ok();
    }

}
