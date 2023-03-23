package com.szcgc.project.vo.system;

import com.szcgc.account.constant.AccountConfigDic;
import com.szcgc.cougua.constant.GuaranteeConfigDic;
import com.szcgc.customer.constant.CustomerConfigDic;
import com.szcgc.finance.constant.FinanceConfigDic;
import com.szcgc.project.constant.ProjectConfigDic;
import io.swagger.v3.oas.annotations.media.Schema;


/**
 * 全局字典
 * @Author liaohong
 * @create 2022/9/21 14:13
 */
public class ConfigVo {

    public static final ConfigVo INSTANCE = new ConfigVo();

    @Schema(description = "账号模块数据字段")
    public AccountConfigDic account = AccountConfigDic.INSTANCE;

    @Schema(description = "客户模块数据字段")
    public CustomerConfigDic customer = CustomerConfigDic.INSTANCE;

    @Schema(description = "反担保模块数据字段")
    public GuaranteeConfigDic guarantee = GuaranteeConfigDic.INSTANCE;

    @Schema(description = "项目模块数据字段")
    public ProjectConfigDic project = ProjectConfigDic.INSTANCE;

    @Schema(description = "财务模块数据字典")
    public FinanceConfigDic finance = FinanceConfigDic.INSTANCE;
}
