package wangliqiu.quartz;

public class Job1 {
	private int i = 1;

	public void doJob() {
		System.out.println(i);// 打印为1 0 0 0 0 。。。 说明springQuartz每次执行doJob都是用的最初的Job1实例，Job1只实例化了一个。
		i = 0;

		System.out.println("dddddddddd");

	}
}
