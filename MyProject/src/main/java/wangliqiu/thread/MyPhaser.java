package wangliqiu.thread;

import java.io.IOException;
import java.util.concurrent.Phaser;

/**
 * Phaser有2个重要状态：phase和party。
 * 
 * @see phase就是阶段，初值为0，Phaser的所有线程执行arrive()后，phase的值自动加1。
 * @see party就是线程数， party=4 就意味着Phaser对象当前管理着4个线程。内部计数在phase加1后自动清零。
 */
public class MyPhaser {

	public static void main(String[] args) throws InterruptedException, IOException {

		Phaser phaser = new Phaser(0) {// 初始线程数，即内部计数最大值（可由phaser.register()动态增大）。
			@Override
			// 当每一个Phaser执行完毕，onAdvance会被自动调用。返回true时，意味着Phaser被终止。
			protected boolean onAdvance(int phase, int registeredParties) {
				System.out.println("===" + phase + "======华丽的分割线=============");

				return registeredParties <= 1;// 当只剩一个线程时（即主线程），phaser终止。
			}
		};

		for (int i = 0; i < 3; i++) {
			new MyTestThread(i + 1, phaser).start();
		}
		// 若phaser的onAdvance()在执行，则register()会阻塞直到onAdvance()完成。
		phaser.register(); // 将主线程加到phaser中，即phaser共管理4个线程。

		while (!phaser.isTerminated()) {
			int n = phaser.arriveAndAwaitAdvance();// 若phaser终止，则返回负数。
			if (n >= 0) {
				System.out.println("第" + n + "个phaser结束！");
			}
		}

	}
}

class MyTestThread extends Thread {
	private int num;
	private Phaser phaser;

	public MyTestThread(int num, Phaser phaser) {
		this.num = num;
		this.phaser = phaser;

		this.phaser.register();
	}

	@Override
	public void run() {
		while (!phaser.isTerminated()) {
			System.out.println("num: " + num);
			// 提前做下一phaser的num校验
			num += 3;
			if (num > 10) {
				phaser.arriveAndDeregister();// 内部计数加1并在phaser中减少一个线程。
				break;
			} else {
				phaser.arriveAndAwaitAdvance();// 内部计数加1并等待，直到下一个phase。
			}
		}
	}

}
