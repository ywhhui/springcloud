package com.szcgc.agreement.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Tuple;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.szcgc.agreement.model.*;
import com.szcgc.agreement.repository.*;
import com.szcgc.agreement.vo.*;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.AbstractMultiValuedMap;
import org.apache.commons.math3.analysis.function.Sin;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class TemplateService {

	@Autowired
	private TemplateRepository templateRepository;

	@Autowired
	private ContractRepository contractRepository;

	@Autowired
	private UnionRepository unionRepository;

	@Autowired
	private KeywordRepository keywordRepository;

	@Autowired
	private ParamRepository paramRepository;

	public TemplateVo retrieveKeywords(long id, String projectCode) {
		String contractSerialNo = "123";
		TemplateEntity template = templateRepository.findById(id);
		Set<UnionEntity> unions = template.getUnions();
		ContractEntity relatedContract = contractRepository.findFirstByProjectCodeOrderByCreatedAtDesc(projectCode);
		Set<ParamEntity> params = relatedContract.getParams();
		Map<Tuple, String> tupleParams = new HashMap<>();
		for (ParamEntity param: params) {
			tupleParams.put(new Tuple(param.getUnion().getId(), param.getKeyword().getId(), param.getIndex()), param.getValue());
		}
		TemplateVo templateVo = new TemplateVo();
		templateVo.id = id;
		templateVo.serialNo = contractSerialNo;
		for (UnionEntity union: unions) {
			switch (union.getType()) {
				case SINGLE:
					if (templateVo.singles == null) {
						templateVo.singles = new ArrayList<>();
					}
					templateVo.singles.add(SingleVo.of(union));
					break;
				case SELECT:
					if (templateVo.selects == null) {
						templateVo.selects = new ArrayList<>();
					}
					templateVo.selects.add(SelectVo.of(union));
					break;
				case STATIC:
					if (templateVo.statics == null) {
						templateVo.statics = new ArrayList<>();
					}
					templateVo.statics.add(UnionVo.of(union));
					break;
				case DYNAMIC:
					if (templateVo.dynamics == null) {
						templateVo.dynamics = new ArrayList<>();
					}
					templateVo.dynamics.add(UnionVo.of(union));
					break;
				case TABLE:
					if (templateVo.tables == null) {
						templateVo.tables = new ArrayList<>();
					}
					templateVo.tables.add(UnionVo.of(union));
					break;
				default:
					throw new IllegalArgumentException("Union type not existed!");
			}
		}
		return templateVo;
	}


	public void saveParams(TemplateVo templateVo) throws IOException {
		Map<String, Object> model = new HashMap<>();
		TemplateEntity template = templateRepository.findById(templateVo.id);
		ContractEntity contract = new ContractEntity();
		contract.setSerialNo(templateVo.serialNo);
		contract.setProjectCode(templateVo.projectCode);
		contract.setTemplate(template);
		Set<ParamEntity> params = new HashSet<>();
		for (SingleVo singleVo: templateVo.singles) {
			UnionEntity union = unionRepository.findById(singleVo.id);
			KeywordEntity keyword = keywordRepository.findById(singleVo.keyword.id);
			Set<ParamEntity> singleParams = singleVo.keyword.toParam(contract, union, keyword);
			params.addAll(singleParams);
			for (ParamEntity paramEntity: singleParams) {
				model.put(paramEntity.getName(), paramEntity.getValue());
			}
		}
		for (UnionVo unionVo: templateVo.statics) {
			UnionEntity union = unionRepository.findById(unionVo.id);
			for (KeywordVo keywordVo: unionVo.keywords) {
				KeywordEntity keyword = keywordRepository.findById(keywordVo.id);
				Set<ParamEntity> staticParams = keywordVo.toParam(contract, union, keyword);
				params.addAll(staticParams);
				for (ParamEntity paramEntity: staticParams) {
					model.put(paramEntity.getName(), paramEntity.getValue());
				}
			}
		}
		for (SelectVo selectVo: templateVo.selects) {
			model.put(selectVo.name, selectVo.value);
			model.put(selectVo.name + "Other", selectVo.other);
		}
		for (UnionVo unionVo: templateVo.dynamics) {
			saveUnion(model, contract, params, unionVo);
		}
		LoopRowTableRenderPolicy rowPolicy = new LoopRowTableRenderPolicy();
		ConfigureBuilder builder = Configure.builder();
		for (UnionVo unionVo: templateVo.tables) {
			builder.bind(unionVo.name, rowPolicy);
			saveUnion(model, contract, params, unionVo);
		}
		Configure config = builder.build();
		String input = template.getFilePath();
		String output = "ibc-agreement/src/test/resources/template/output.docx";
		XWPFTemplate doc = XWPFTemplate.compile(input, config).render(model);
		doc.writeToFile(output);

		System.out.println(new ObjectMapper().writeValueAsString(model));

		contract.setParams(params);
		contract.setFilePath(output);
		contract.setName("test");
//		contractRepository.save(contract);
//		paramRepository.saveAll(params);
	}

	private void saveUnion(Map<String, Object> model, ContractEntity contract, Set<ParamEntity> params, UnionVo unionVo) {
		UnionEntity union = unionRepository.findById(unionVo.id);
		List<Map<String, Object>> table = new ArrayList<>();
		model.put(unionVo.name, table);
		for (KeywordVo keywordVo: unionVo.keywords) {
			KeywordEntity keyword = keywordRepository.findById(keywordVo.id);
			Set<ParamEntity> unionParams = keywordVo.toParam(contract, union, keyword);
			params.addAll(unionParams);
			for (int i = 0; i < keywordVo.values.size(); i++) {
				if (table.size() < keywordVo.values.size()) {
					int finalI = i;
					table.add(new HashMap<String, Object>(){{
						put("idx", Convert.numberToChinese(finalI +1, false));
					}});
				}
				Map<String, Object> row = table.get(i);
				row.put(keywordVo.name, keywordVo.values.get(i));
			}
		}
	}
}
