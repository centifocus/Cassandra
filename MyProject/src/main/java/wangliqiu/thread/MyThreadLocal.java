package wangliqiu.thread;

public class MyThreadLocal {
	// 重写initialValue()方法，指定初始值
	private static ThreadLocal<Integer> seqNum = new ThreadLocal<Integer>() {
		@Override
		public Integer initialValue() {
			return 0;
		}
	};

	// 改变值
	public int getNextNum() {
		seqNum.set(seqNum.get() + 1);
		return seqNum.get();
	}

	public static void main(String[] args) {
		MyThreadLocal sn = new MyThreadLocal();
		// 3个线程共享sn
		new TestClient(sn).start();
		new TestClient(sn).start();
		new TestClient(sn).start();
	}

}

class TestClient extends Thread {
	private MyThreadLocal sn;

	public TestClient(MyThreadLocal sn) {
		this.sn = sn;
	}

	@Override
	public void run() {
		for (int i = 0; i < 3; i++) {
			System.out.println(Thread.currentThread().getName() + "->" + sn.getNextNum());

		}
	}
}
