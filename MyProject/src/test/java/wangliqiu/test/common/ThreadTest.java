package wangliqiu.test.common;

import org.junit.Test;

public class ThreadTest {

	@Test
	public void test1() throws InterruptedException {

		Thread thread = new Thread(() -> {

			while (true) {
				try { // do something
					throw new Exception("wangliqiu");
				} catch (Exception e) {
					try {
						System.out.println(e);
						Thread.sleep(1000);

						throw new Exception("viviying");
					} catch (Exception e1) {
						System.out.println(e1);
					}
				}
			}

		});
		thread.start();

		Thread.sleep(100000);
	}

}
