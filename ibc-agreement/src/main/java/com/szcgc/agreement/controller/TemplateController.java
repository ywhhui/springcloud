package com.szcgc.agreement.controller;

import com.szcgc.agreement.service.TemplateService;
import com.szcgc.agreement.vo.TemplateVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@RestController
@RequestMapping("/template")
public class TemplateController {

	@Autowired
	private TemplateService templateService;

	@GetMapping("/acquireKeywords")
	public TemplateVo acquireKeywords(@RequestParam("id") long id) {
		return templateService.retrieveKeywords(id, "123");
	}

	@PostMapping("/saveParams")
	public void saveParams(@RequestBody TemplateVo params) throws IOException {
		templateService.saveParams(params);
	}
}
