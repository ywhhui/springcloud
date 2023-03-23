package com.szcgc.agreement.vo;

import com.szcgc.agreement.model.ContractEntity;
import com.szcgc.agreement.model.KeywordEntity;
import com.szcgc.agreement.model.ParamEntity;
import com.szcgc.agreement.model.UnionEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KeywordVo {

	public long id;

	public String name;

	public List<String> values;

	public String description;

	public String type;

	public static KeywordVo of(KeywordEntity entity) {
		KeywordVo vo = new KeywordVo();
		vo.id = entity.getId();
		vo.name = entity.getTag();
		vo.description = entity.getDescription();
		vo.type = entity.getType().name();
		return vo;
	}

	public static List<KeywordVo> of(Set<KeywordEntity> entities) {
		List<KeywordVo> vos = new ArrayList<>(entities.size());
		for (KeywordEntity entity: entities) {
			vos.add(of(entity));
		}
		return vos;
	}

	public Set<ParamEntity> toParam(ContractEntity contract, UnionEntity union, KeywordEntity keyword) {
		Set<ParamEntity> params = new HashSet<>();
		for (String value: values) {
			ParamEntity param = new ParamEntity();
			param.setContract(contract);
			param.setUnion(union);
			param.setKeyword(keyword);
			param.setName(name);
			param.setValue(value);
			params.add(param);
		}
		return params;
	}

}
