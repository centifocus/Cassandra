package wangliqiu.quartz;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.scheduling.quartz.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestOfSpringQuartz {

	@Autowired
	ApplicationContext appCtx;

	@Autowired
	SchedulerFactoryBean scheduler;

	@Test
	public void test1() throws InterruptedException {
		// spring会自动执行scheduler.start()

		Thread.sleep(1000000);
	}

}
