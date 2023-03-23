package com.szcgc.agreement.vo;

import com.szcgc.agreement.model.KeywordEntity;
import com.szcgc.agreement.model.UnionEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SelectVo {

	public long id;

	public String name;

	public String description;

	public List<String> keywords;

	public String value;

	public String other;

	public static SelectVo of(UnionEntity union) {
		SelectVo vo = new SelectVo();
		vo.id = union.getId();
		vo.name = union.getTag();
		vo.description = union.getDescription();
		Set<KeywordEntity> keywords = union.getKeywords();
		vo.keywords = new ArrayList<>(keywords.size());
		for (KeywordEntity keyword: keywords) {
			vo.keywords.add(keyword.getDescription());
		}
		return vo;
	}

}
