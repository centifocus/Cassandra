package spring4.release;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

//当用该类时才实例化
@Lazy
@Component("man")
public class Man {

	public String name;
	public String sex;
	public String age;
	public Home home;
	public static String id;

	// 当用该filed是才注入
	@Lazy
	// 若容器中没有Bar，它将自动装配null。required默认为true
	@Autowired(required = false)
	// 将@Autowired的byType变成byId注入
	@Qualifier(value = "bar1")
	public Bar[] bars;

	public Man() {
		System.out.println("Man is initialized!!!");
	}

}
