package design.observer.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-design.observer.xml" })
public class Test1 {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private RegisterService registerService;

	@Test
	public void testPublishEvent() throws InterruptedException {
		// 由于ApplicationContext的配置文件中<context:component-scan >把所有Listener注册进去了。
		applicationContext.publishEvent(new MyEvent("this is MyEvent！！！"));

		registerService.register("sssssssss");

		Thread.sleep(10000);
	}

}
