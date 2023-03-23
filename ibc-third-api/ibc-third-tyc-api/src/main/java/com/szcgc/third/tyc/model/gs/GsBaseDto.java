package com.szcgc.third.tyc.model.gs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 工商信息
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GsBaseDto {

	public String staffNumRange; // String varchar(200) 人员规模
	public long fromTime; // Number 毫秒数 经营开始时间
	public int type;// Number 法人类型，1 人 2 公司
	public String bondName;// String varchar(20) 股票名
	public long id; // Number 企业id
	public int isMicroEnt;// Number 是否是小微企业 0不是 1是
	public String usedBondName;// String varchar(20) 股票曾用名
	public String regNumber;// String varchar(31) 注册号
	public int percentileScore;// Number 万分制 企业评分
	public String regCapital;// String varchar(50) 注册资本
	public String phoneNumber;// String	varchar(255)	企业联系方式
	public String name;// String varchar(255) 企业名
	public String regInstitute;// String varchar(255) 登记机关
	public String regLocation;// String varchar(255) 注册地址
	public String industry;// String varchar(255) 行业
	public long approvedTime;// Number 毫秒数 核准时间
	public int socialStaffNum;// Number 参保人数
	public String tags;// String varchar(255) 企业标签
	public String taxNumber;// String varchar(255) 纳税人识别号
	public String businessScope;// String varchar(4091) 经营范围
	public String property3;// String varchar(255) 英文名
	public String alias;// String varchar(255) 简称
	public String orgNumber;// String varchar(31) 组织机构代码
	public String regStatus;// String varchar(31) 企业状态
	public long estiblishTime;// Number 毫秒数 成立日期
	public String bondType;// String varchar(31) 股票类型
	public String legalPersonName;// String varchar(120) 法人
	public long toTime;// Number 毫秒数 经营结束时间
	public String actualCapital;// String varchar(50) 实收注册资金
	public String companyOrgType;// String varchar(127) 企业类型
	public String base;// String varchar(31) 省份简称
	public String creditCode;// String varchar(255) 统一社会信用代码
	public String historyNames;// String varchar(255) 曾用名
	public String bondNum;// String varchar(20) 股票号
	// public String regCapitalCurrency String varchar(10) 注册资本币种 人民币 美元 欧元 等(暂未使用)
	// public String actualCapitalCurrency String varchar(10) 实收注册资本币种 人民币 美元 欧元
	// 等(暂未使用)
	public long revokeDate;// Number 毫秒数 吊销日期
	public String revokeReason;// String varchar(500) 吊销原因
	public long cancelDate;// Number 毫秒数 注销日期
	public String cancelReason;// String varchar(500) 注销原因

	@Override
	public String toString() {
		return "GsBaseInfo{" +
				"staffNumRange='" + staffNumRange + '\'' +
				", fromTime=" + fromTime +
				", type=" + type +
				", bondName='" + bondName + '\'' +
				", id=" + id +
				", isMicroEnt=" + isMicroEnt +
				", usedBondName='" + usedBondName + '\'' +
				", regNumber='" + regNumber + '\'' +
				", percentileScore=" + percentileScore +
				", regCapital='" + regCapital + '\'' +
				", name='" + name + '\'' +
				", regInstitute='" + regInstitute + '\'' +
				", regLocation='" + regLocation + '\'' +
				", industry='" + industry + '\'' +
				", approvedTime=" + approvedTime +
				", socialStaffNum=" + socialStaffNum +
				", tags='" + tags + '\'' +
				", taxNumber='" + taxNumber + '\'' +
				", businessScope='" + businessScope + '\'' +
				", property3='" + property3 + '\'' +
				", alias='" + alias + '\'' +
				", orgNumber='" + orgNumber + '\'' +
				", regStatus='" + regStatus + '\'' +
				", estiblishTime=" + estiblishTime +
				", bondType='" + bondType + '\'' +
				", legalPersonName='" + legalPersonName + '\'' +
				", toTime=" + toTime +
				", actualCapital='" + actualCapital + '\'' +
				", companyOrgType='" + companyOrgType + '\'' +
				", base='" + base + '\'' +
				", creditCode='" + creditCode + '\'' +
				", historyNames='" + historyNames + '\'' +
				", bondNum='" + bondNum + '\'' +
				", revokeDate=" + revokeDate +
				", revokeReason='" + revokeReason + '\'' +
				", cancelDate=" + cancelDate +
				", cancelReason='" + cancelReason + '\'' +
				'}';
	}
}
