package socketNIO;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Processor extends Thread {

	private static final int TimeOut = 1000; // 选择器选择超时时间
	private Lock lock = new ReentrantLock();
	private String threadName;
	private Selector selector;
	private Queue<SocketChannel> channelList;
	private Queue<SocketChannel> waitForSendSocketQueue;

	public Processor(String threadName) {
		this.threadName = threadName;
		this.channelList = new ConcurrentLinkedQueue<SocketChannel>();
		this.waitForSendSocketQueue = new ArrayBlockingQueue<SocketChannel>(128);
		try {
			this.selector = Selector.open();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		for (;;) {
			try {
				this.acceptChannel();
				this.registerWriteEvent();

				if (selector.select(TimeOut) > 0) {
					Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
					while (iterator.hasNext()) {
						SelectionKey key = iterator.next();
						iterator.remove();

						if (!key.isValid()) {
							continue;

						} else if (key.isReadable()) {
							((DTask) key.attachment()).onDataRecieve();
							// key.interestOps(SelectionKey.OP_WRITE);

						} else if (key.isWritable()) {
							// ((DTask)key.attachment()).onDataSend();
							// key.interestOps(SelectionKey.OP_READ);

						} else if (key.isConnectable()) {
							//

						} else {

						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void acceptChannel() throws IOException {
		for (;;) {
			SocketChannel channel = this.channelList.poll();// 获取并移除此队列的头，如果此队列为空，则返回 null。
			if (channel != null) {
				channel.configureBlocking(false);
				DTask task = new DTask(this, channel);
				channel.register(selector, SelectionKey.OP_READ, task);// 该线程selector注册到了传入的channel

				System.out.println(this.threadName + " Processor register url: "
						+ channel.socket().getInetAddress().getHostAddress() + ":" + channel.socket().getPort());
			} else {
				break;
			}

		}
	}

	private void registerWriteEvent() {
		try {
			lock.lock();
			for (;;) {
				SocketChannel channel = this.waitForSendSocketQueue.poll();
				if (channel != null) {
					SelectionKey key = channel.keyFor(this.selector);// 返回当前通道由指定选择器给的键
					if (key != null) {
						key.interestOps(SelectionKey.OP_WRITE);
					}
				} else {
					break;
				}
			}
		} finally {
			lock.unlock();
		}
	}

	public void registerForSend(SocketChannel channel) {
		try {
			lock.lock();
			this.waitForSendSocketQueue.add(channel);
			this.selector.wakeup();// 阻塞在 select()
									// 的某一线程被wakeup()中断，并select()立即返回//若没有select()，则下一次select()立即返回。
		} finally {
			lock.unlock();
		}
	}

	public void addChannel(SocketChannel channel) {
		this.channelList.offer(channel);// add
		System.out.println(this.threadName + " Processor addChannel url: "
				+ channel.socket().getInetAddress().getHostAddress() + ":" + channel.socket().getPort());
	}

}
