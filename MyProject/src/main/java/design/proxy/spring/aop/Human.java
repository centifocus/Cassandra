package design.proxy.spring.aop;

public class Human implements Sleepable {

	@Override
	public String sleep(String name) {
		System.out.println(name + " 睡觉了！");

		return name;

	}

}
