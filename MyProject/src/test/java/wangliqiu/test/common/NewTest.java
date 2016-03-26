package wangliqiu.test.common;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.WebUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ideal.crm.star.dto.HoldStarRightDTO;

import wangliqiu.util.IOUtil;

public class NewTest {
	protected static Logger logger = Logger.getLogger(NewTest.class);

	@Before
	public void init() {

	}

	// @Test
	public void execute() throws Exception {

		final String SERVER_URL = "http://test.csb.sh.ctc.com:7805/openit/class_1?ServiceName=DoTally&ServiceVer=1.0&Consumer=CRM&op=doTally"; // 定义需要获取的内容来源地址

		RestTemplate restTemplate = new RestTemplate();
		String request = IOUtil.scan("D:/converted.xml");
		String string = restTemplate.postForEntity(SERVER_URL, request, String.class).getBody();
		System.out.println(string);
	}

	@Test
	public void just() throws FileNotFoundException {

		final String SERVER_URL = "http://10.145.205.73:9092/shcrm-star-webapp/HoldStarRight";

		RestTemplate restTemplate = new RestTemplate();
		String body = IOUtil.scan("D:/test.json");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(body, headers);

		String string = restTemplate.postForEntity(SERVER_URL, entity, String.class).getBody();
		System.out.println(string);

	}
}
