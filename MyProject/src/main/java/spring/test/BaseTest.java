package spring.test;

import java.io.FileNotFoundException;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ResourceUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring4.xml" })
public class BaseTest extends AbstractJUnit4SpringContextTests {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected String classpath;

	public BaseTest() {
		try {
			classpath = ResourceUtils.getURL("classpath:").getPath();
			System.out.println("classpath :" + classpath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected final String classpath() throws FileNotFoundException {
		return ResourceUtils.getURL("classpath:").toString();

	}

}
