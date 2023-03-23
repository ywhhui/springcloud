package com.szcgc.third.tyc.model.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @Author liaohong
 * @create 2022/9/20 18:08
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchItemDto {
    public String regNumber;//	String	varchar(31)	注册号
    public String regStatus;//	String	varchar(31)	经营状态
    public String creditCode;//	String	varchar(255)	统一社会信用代码
    public String estiblishTime;//	String	日期	成立日期
    public String regCapital;//	String	varchar(50)	注册资本
    public String companyType;//	Number	smallint(6)	机构类型-1：公司；2：香港企业；3：社会组织；4：律所；5：事业单位；6：基金会；7-不存在法人、注册资本、统一社会信用代码、经营状态;8：台湾企业；9-新机构
    public String name;//	String	varchar(255)	公司名
    public String id;//	Number	bigint(20)	公司id
    public String orgNumber;//	String	varchar(31)	组织机构代码
    public String type;//	Number	int(11)	1-公司 2-人
    public String base;//	String	varchar(255)	省份
    public String legalPersonName;//	String	varchar(255)	法人
    public String matchType;//	String	varchar(255)	匹配原因
}
