package com.szcgc.account.controller;

import com.szcgc.account.model.system.BankBranchInfo;
import com.szcgc.account.model.system.BankInfo;
import com.szcgc.account.service.IBankBranchService;
import com.szcgc.account.service.IBankService;
import com.szcgc.account.vo.BankVo;
import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.web.WebConstants;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author liaohong
 * @create 2022/9/21 14:12
 */
@Api(tags = "系统配置")
@RestController
public class SystemController {

    @Autowired
    IBankService bankService;

    @Autowired
    IBankBranchService bankBranchService;

//    @Operation(summary = "数据字典")
//    @GetMapping("config")
//    public IbcResponse<ConfigVo> config() {
//        return IbcResponse.ok(ConfigVo.INSTANCE);
//    }

    @Operation(summary = "银行详情")
    @GetMapping("banks" + WebConstants.DETAIL)
    public IbcResponse<List<BankVo>> detailBank() {
        List<BankInfo> banks = bankService.findAll();
        List<BankBranchInfo> branches = bankBranchService.findAll();
        Map<Integer, List<BankBranchInfo>> mBranches = branches.stream().collect(Collectors.groupingBy(BankBranchInfo::getBankId));
        List<BankVo> vos = banks.stream().map(bank -> BankVo.cvt(bank, mBranches.get(bank.getId()))).collect(Collectors.toList());
        return IbcResponse.ok(vos);
    }
}
