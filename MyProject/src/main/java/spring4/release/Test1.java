package spring4.release;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.DirectFieldAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.BackOffExecution;
import org.springframework.util.backoff.FixedBackOff;
import org.junit.runners.MethodSorters;

/**
 * 
 * @Service 业务层组件
 * @Controller 控制层组件
 * @Repository 持久层，即DAO组件
 * @Component 泛指组件，当组件不好归类的时候，我们可以使用这个注解进行标注。
 *
 * 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring4.release.xml" })
// 按方法名排序来测试
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Test1 {

	@Autowired
	ApplicationContext appCtx;
	@Autowired
	Map<String, Bar> map;
	@Autowired
	List<Bar> list;

	// @Test
	public void test1DirectFieldAccessor() {
		Man man = (Man) appCtx.getBean("man");

		DirectFieldAccessor accessor = new DirectFieldAccessor(man);
		accessor.setAutoGrowNestedPaths(true);// 如果嵌套对象为null，字段创建
		accessor.setPropertyValue("name", "zhangsan");
		accessor.setPropertyValue("home", new Home());
		accessor.setPropertyValue("home.address", "dddd");

		System.out.println(accessor.getPropertyValue("name") + "   " + man.name);
		System.out.println(man.home.address);

	}

	// @Test
	public void test2Annotation() {
		Man man = (Man) appCtx.getBean("man");
		System.out.println(man.bars.length + "   " + man.bars[0].name);
	}

	// @Test
	public void test3Map() {
		// 将容器中所有符合类型的bean注入到map，key为id
		for (Entry<String, Bar> entry : map.entrySet()) {
			System.out.println(entry.getKey() + "  " + entry.getValue().name);
		}
		for (Bar bar : list) {
			System.out.println(bar.name);
		}
	}

	@Test
	public void test4Lazy() {
		System.out.println("appCtx is @Autowired!!!");
		Man man = (Man) appCtx.getBean("man");
	}

}
