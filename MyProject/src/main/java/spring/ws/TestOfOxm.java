package spring.ws;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.junit.Test;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import spring.oxm.Man.Card;
import spring.test.BaseTest;

public class TestOfOxm extends BaseTest {

	@Test
	public void justTest() throws IOException {
		List<Home> homes = new ArrayList<>();
		homes.add(new Home("shanghai"));
		homes.add(new Home("beijing"));
		Man man = new Man("wangliqiu", true, 27, homes);
		List<Card> cards = new ArrayList<>();
		cards.add(new Card("ideal1"));
		cards.add(new Card("ideal2"));
		man.cards = cards;

		// BeanUtils.get
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setPackagesToScan(new String[] { "spring.oxm" });
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		// 对象转xml
		marshaller.marshal(man, new StreamResult(bos));
		String xml = new String(bos.toByteArray());
		logger.info("xml: \n" + xml);

		// xml转对象，当对象属性有List，则不能互转
		Source source = new StreamSource(classpath() + "testdata-spring.oxm.xml");
		Man targetMan = (Man) marshaller.unmarshal(source);
		logger.info(targetMan.homes.get(0).address);

		// 对象转json
		ObjectMapper mapper = new ObjectMapper();
		String Json = mapper.writeValueAsString(man);
		logger.info("json: \n" + Json);

		// json获取属性
		JsonNode root = mapper.readTree(Json);
		String item = root.path("items").get(0).path("row_id").asText();// 对于list要get()
		System.out.println(item);
		for (JsonNode node : root.path("items")) {

		}

		// json转对象
		Man man2 = mapper.readValue(Json, Man.class);
		logger.info(man2.homes.get(0).address + man2.isMale + man2.age + man2.cards.get(0).msg);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(new Date()));

	}

}
