package com.szcgc.customer.controller;

import cn.hutool.core.util.StrUtil;
import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.web.IbcId;
import com.szcgc.comm.web.WebConstants;
import com.szcgc.customer.model.Custodian;
import com.szcgc.customer.service.CustodianService;
import com.szcgc.customer.util.ParamVerifyUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 管户
 *
 * @author chenjiaming
 * @date 2022-10-10 14:42:33
 */
@Slf4j
@Api(tags = "管户")
@RestController
@RequestMapping("custodian")
public class CustodianController {

    @Resource
    private CustodianService custodianService;

    @Operation(summary = "修改管护数据")
    @PostMapping(WebConstants.UPDATE)
    public IbcResponse edit(@ApiParam(hidden = true) @IbcId int accountId,
                            @RequestBody Custodian custodian) {
        String errorTips = ParamVerifyUtil.verify(custodian);
        if (StrUtil.isNotBlank(errorTips)) {
            return IbcResponse.error500(errorTips);
        }

        custodianService.edit(accountId, custodian.getCustomerId(), custodian.getCustodianId());
        return IbcResponse.ok();
    }

}
