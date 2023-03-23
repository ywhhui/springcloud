package com.szcgc.account.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.szcgc.account.model.system.BankBranchInfo;
import com.szcgc.account.model.system.BankInfo;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author liaohong
 * @create 2020/9/24 14:58
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankVo {

    @Schema(description = "银行Id")
    public int value;
    @Schema(description = "银行名")
    public String label;
    @Schema(description = "是否支持电子保函")
    public int eBh;
    public List<BankItemVo> children;

    public static BankVo cvt(BankInfo bank, List<BankBranchInfo> branches) {
        BankVo vo = new BankVo();
        vo.value = bank.getId();
        vo.label = bank.getName();
        vo.eBh = bank.getElecBh();
        if (branches != null && branches.size() > 0) {
            vo.children = branches.stream().map(BankItemVo::cvt).collect(Collectors.toList());
        }
        return vo;
    }

    public static class BankItemVo {
        public int value;
        public String label;

        public static BankVo.BankItemVo cvt(BankBranchInfo info) {
            BankVo.BankItemVo vo = new BankVo.BankItemVo();
            vo.value = info.getId();
            vo.label = info.getName();
            return vo;
        }
    }
}
