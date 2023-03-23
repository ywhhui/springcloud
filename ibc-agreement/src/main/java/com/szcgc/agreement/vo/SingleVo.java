package com.szcgc.agreement.vo;

import cn.hutool.core.lang.Tuple;
import com.szcgc.agreement.model.UnionEntity;

import java.util.Map;


public class SingleVo {

	public long id;

	public KeywordVo keyword;

	public static SingleVo of(UnionEntity union) {
		SingleVo vo = new SingleVo();
		vo.id = union.getId();
		if (union.getKeywords().size() != 1) {
			throw new IllegalStateException("More than 1 keyword in single " + union.getId());
		}
		vo.keyword = KeywordVo.of(union.getKeywords().iterator().next());
		return vo;
	}
}
