package com.szcgc.agreement.vo;

import java.util.List;

public class TemplateVo {

	public long id;

	public String serialNo;

	public String projectCode;

	public String templateName;

	public List<SingleVo> singles;

	public List<SelectVo> selects;

	public List<UnionVo> statics;

	public List<UnionVo> dynamics;

	public List<UnionVo> tables;
}
