package spring.annotation.conditional;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.annotation.conditional1.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Test2 {

	@Autowired
	ApplicationContext appCtx;

	@Autowired
	MyBean bean;

	@Test
	public void test1() {
		bean.say();
	}

}
