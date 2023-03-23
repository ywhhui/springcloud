package com.szcgc.agreement.vo;

import com.szcgc.agreement.model.UnionEntity;

import java.util.List;

public class UnionVo {

	public long id;

	public String name;

	public String description;

	public String type;

	public List<KeywordVo> keywords;

	public static UnionVo of(UnionEntity union) {
		UnionVo vo = new UnionVo();
		vo.id = union.getId();
		vo.name = union.getTag();
		vo.description = union.getDescription();
		vo.type = union.getType().name();
		vo.keywords = KeywordVo.of(union.getKeywords());
		return vo;
	}
}
