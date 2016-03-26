package wangliqiu.thread;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * m个线程等待另外N个线程都执行到countDown()，再继续执行 。 CountDownLatch和CyclicBarrier在实现功能上无差别。 调用 countDown()
 * 来计数，在计数到达零之前await()会一直受阻塞。
 */
class MyThread extends Thread {

	private CountDownLatch latch;
	private int time;// 用时

	public MyThread(CountDownLatch latch, int time) {
		this.latch = latch;
		this.time = time;
	}

	@Override
	public void run() {
		try {
			work();
			System.out.println(Thread.currentThread().getName() + "spend:" + time);
			latch.countDown(); // 计数
		} catch (InterruptedException e) {

		}
	}

	private void work() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(time);
	}

}

class otherThreads extends Thread {

	private CountDownLatch latch;
	private String name;

	public otherThreads(CountDownLatch latch, String name) {
		this.latch = latch;
		this.name = name;
	}

	@Override
	public void run() {
		try {
			System.out.println("Thread" + name + "await!!!");
			latch.await();
			System.out.println("Thread" + name + "begin!!!");
		} catch (InterruptedException e) {

		}
	}

}

public class MyCountDownLatch {
	final static int SIZE = 3;

	public static void main(String[] args) throws InterruptedException {
		CountDownLatch latch = new CountDownLatch(SIZE); // 初始化计数20

		new otherThreads(latch, "Thread 1111").start();
		new otherThreads(latch, "Thread 2222").start();
		for (int i = 0; i < SIZE; i++) {
			new MyThread(latch, 5000).start();
		}
		System.out.println("Main Thread await!!!");
		latch.await();

		System.out.println("Main Thread execute!!!");

	}

}