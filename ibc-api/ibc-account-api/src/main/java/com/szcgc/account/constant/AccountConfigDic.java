package com.szcgc.account.constant;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 客户模块字典
 * @Author liaohong
 * @create 2022/9/21 14:13
 */
public class AccountConfigDic {

    public static final AccountConfigDic INSTANCE = new AccountConfigDic();

    @Schema(description = "账号- 状态")
    public Map<String, String> accountStatus = new HashMap<>(2);
    @Schema(description = "账号-性别")
    public Map<String, String> accountSex = new HashMap<>(2);
    @Schema(description = "账号-角色")
    public Map<String, String> accountRole = new HashMap<>(20);

    private AccountConfigDic() {

        accountStatus.put(String.valueOf(AccountConstant.STATUS_NML), "正常");
        accountStatus.put(String.valueOf(AccountConstant.STATUS_DEL), "禁用");

        accountSex.put(String.valueOf(AccountConstant.SEX_MAL), "男");
        accountSex.put(String.valueOf(AccountConstant.SEX_FEMALE), "女");

        Arrays.stream(AccountRoleEnum.values()).forEach(item -> accountRole.put(item.name(), item.getCnName()));
    }
}
