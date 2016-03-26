package wangliqiu.test.lambda;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.xml.DOMConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyTest implements MyInterf, MyInterf1 {
	private final static Logger logger = LoggerFactory.getLogger(MyTest.class);

	static {
		DOMConfigurator.configure(System.getProperty("user.dir") + "/src/main/resources/log4j.xml");
	}

	private UnaryOperator<Integer> factorial;

	public void m1() {

	}

	public String m2() {
		return MyInterf.super.m2();
	}

	@Before
	public void test1() {

	}

	@Test
	public void dojob() throws Exception {

		Converter<String, Integer> converter = (from) -> {
			return Integer.valueOf(from);
		};
		logger.info(converter.convert("123").toString());

		Thread thread = new Thread(() -> {
			System.out.println("lambda exp.");
		});
		thread.start();

		List<String> list = Arrays.asList(new String[] { "aaa", "bbb" });
		list.forEach(System.out::println);

		// 嵌套的λ表达式
		Callable<Runnable> c1 = () -> () -> {
			System.out.println("Nested lambda");
		};
		c1.call().run();

		// 用在条件表达式中
		boolean flg = true;
		Callable<String> c2 = flg ? (() -> "true") : (() -> "false");
		System.out.println(c2.call());

		// 递归函数
		factorial = i -> i == 0 ? 1 : i * factorial.apply(i - 1);
		System.out.println(factorial.apply(4));// 1*2*3*4

		// 生成5个随机数
		Stream.generate(Math::random).limit(5).forEach(System.out::println);

	}

	@After
	public void test2() {
		// 获取大于10的不重复的元素
		List<String> list = Arrays.asList(new String[] { "3", "7", "12", "13", "13", "20" });
		List<Integer> r = list.stream().map(e -> new Integer(e)).filter(e -> e > 10).distinct()
				.collect(Collectors.toList());
		System.out.println(r);
		assertEquals(3, r.size());
	}

}
