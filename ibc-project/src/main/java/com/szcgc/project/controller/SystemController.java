package com.szcgc.project.controller;


import com.szcgc.comm.IbcResponse;
import com.szcgc.project.vo.system.BusinessTypeVo;
import com.szcgc.project.vo.system.ConfigVo;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 字典相关
 *
 * @Author liaohong
 * @create 2022/9/21 14:12
 */
@Api(tags = "系统配置")
@RestController
@RequestMapping("system")
public class SystemController {

    @Operation(summary = "全局数据字典")
    @GetMapping("global/config")
    public IbcResponse<ConfigVo> config() {
        return IbcResponse.ok(ConfigVo.INSTANCE);
    }

    @Operation(summary = "业务品种")
    @GetMapping("business")
    public IbcResponse<BusinessTypeVo> businessType() {
        return IbcResponse.ok(BusinessTypeVo.INSTANCE);
    }

}
