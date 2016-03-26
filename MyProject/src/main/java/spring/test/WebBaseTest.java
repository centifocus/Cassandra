package spring.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
// declare that the ApplicationContext loaded for an integration test should be a WebApplicationContext.
// must be used in conjunction with @ContextConfiguration
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath*:spring4.xml", "classpath*:spring-*.xml" })
// @TransactionConfiguration(defaultRollback = true)
public class WebBaseTest {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	protected WebApplicationContext webAppCtx;
	protected MockMvc mockMvc;

	// The @Before methods of superclasses will be run before those of the current class, unless they are overridden in
	// the current class.
	@Before
	public void setup() {
		// webAppContextSetup 构造的WEB容器可以添加fileter
		// WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		// 注意import static 静态方法webAppContextSetup
		mockMvc = webAppContextSetup(webAppCtx).build();
	}

	// example of subclass test
	@Test
	public void test() throws Exception {
		ResultActions ras = mockMvc.perform(get("/test/xml").contentType(MediaType.APPLICATION_XML).content(""));
		ras.andExpect(status().isOk()).andDo(print());

	}

}