package com.szcgc.agreement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.szcgc.agreement.service.TemplateService;
import com.szcgc.agreement.vo.TemplateVo;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AgreementApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-dev.properties")
public class TemplateAPITest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TemplateService templateService;

	@Autowired
	private ObjectMapper mapper;

	private TemplateVo templateVo;

	@Test
	public void testRetrieveKeywords() throws Exception {
		mockMvc.perform(get("/template/acquireKeywords")
				.queryParam("id", "1"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string("1"));
	}

//	@BeforeEach
//	public void assembleTemplateVo() {
//		templateVo = templateService.retrieveKeywords(1, "1");
//	}

	@Test
	public void testSaveParams() throws Exception {
		mockMvc.perform(post("/template/saveParams")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapper.writeValueAsString(templateVo)))
				.andExpect(status().isOk())
				.andExpect(content().string("1"));
	}
}
