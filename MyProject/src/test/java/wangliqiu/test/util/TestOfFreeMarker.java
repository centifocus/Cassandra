package wangliqiu.test.util;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

public class TestOfFreeMarker {
	private static final Logger logger = LoggerFactory.getLogger(TestOfFreeMarker.class);

	private Configuration cfg = new Configuration(new Version(2, 3, 23));// 注明版本，因为版本会不兼容。

	@Test
	public void justTest() throws IOException, TemplateException {
		cfg.setDirectoryForTemplateLoading(new File(System.getProperty("user.dir") + "/src/main/resources"));
		Template temp = cfg.getTemplate("test.ftl", "UTF-8");

		Map<String, Object> map = generateData();
		Writer out = new StringWriter();
		temp.process(map, out);

		System.out.println(out.toString());

	}

	public Map<String, Object> generateData() {
		Map<String, Object> map = new HashMap<>();
		map.put("serviceName", "test");
		map.put("name", "wangviviying");
		List<List> items = new ArrayList<>();
		map.put("items", items);
		List<Map<String, String>> item = new ArrayList<>();
		items.add(item);
		Map<String, String> map1 = new HashMap<>();
		map1.put("value", "sssssss");
		item.add(map1);
		Map<String, String> map2 = new HashMap<>();
		map2.put("value", "fffffffff");
		item.add(map2);

		return map;
	}
}
