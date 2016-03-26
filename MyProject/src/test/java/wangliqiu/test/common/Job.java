package wangliqiu.test.common;

public class Job {

	public void doJob(@ParmInsert(name = "viviying") String name, @ParmInsert() String sex) {
		System.out.println(name + "  " + sex);
	}

}
