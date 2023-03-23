package com.szcgc.agreement.service;

import cn.hutool.core.convert.Convert;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import jdk.nashorn.internal.runtime.regexp.joni.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordMaker {

	static String input = "ibc-agreement/src/test/resources/template/dyfdb.docx";
	static String output = "ibc-agreement/target/output.docx";

	public static void main(String[] args){
		System.out.println(System.getProperty("user.dir"));
		List<Map<String, Object>> roleBs = new ArrayList<>(5);
		List<Map<String, Object>> properties = new ArrayList<>(5);
		for (int i = 0; i < 5; i++) {
			String finalI = Convert.numberToChinese(i+1, false);
			roleBs.add(new HashMap<String, Object>() {{
				put("idx", finalI);
				put("name", "name" + finalI);
				put("address", "address" + finalI);
				put("contactName", "contactName" + finalI);
				put("contactNum", "contactNum" + finalI);
				put("email", "email" + finalI);
			}});

			properties.add(new HashMap<String, Object>() {{
				put("idx", finalI);
				put("owner", "owner" + finalI);
				put("cert", "cert" + finalI);
				put("address", "address" + finalI);
			}});

		}
		try {
			LoopRowTableRenderPolicy rowPolicy = new LoopRowTableRenderPolicy();
			Configure config = Configure.builder()
					.bind("property", rowPolicy)
					.bind("vehicle", rowPolicy)
					.bind("machine", rowPolicy)
					.build();
			Map<String, Object> data = new HashMap<String, Object>() {{
				put("roleB", roleBs);
				put("property", properties);
			}};
			XWPFTemplate template = XWPFTemplate.compile(input, config).render(data);

			template.writeToFile(output);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
