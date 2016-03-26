package wangliqiu.thread;

import java.util.concurrent.locks.StampedLock;

/**
 * StampedLock性能比ReentrantReadWriteLock高，主要在有大量的读线程和少量的写线程的情况下。
 * 
 * @see 写锁是exclusive锁，读锁是non-exclusive锁（即readLock与readLock始终不冲突）
 * @see readLock在乐观模式下与writeLock不冲突，在悲观模式下冲突
 * @see 乐观模式：即有大量的读线程和少量的写线程
 */
public class MyStampedLock {

	public StampedLock sl = new StampedLock();
	public double data = 0;

	public void writeExclusively(double delta) { // 线程写操作一定是互斥的
		long stamp = sl.writeLock();
		try {
			data += delta;
		} finally {
			sl.unlockWrite(stamp);
		}
	}

	public double readOptimisticly() {
		long stamp = sl.tryOptimisticRead(); // 获得一个乐观读锁
		double current = data;
		if (!sl.validate(stamp)) { // 检查发出乐观读锁后同时是否有其他写锁发生，没有返回true
			stamp = sl.readLock(); // 若发生写锁，则获得一个悲观读锁
			try {
				current = data;// do again
			} finally {
				sl.unlockRead(stamp);
			}
		}

		return current;
	}

	public double readAndWrite(double delta) {
		long stamp = sl.tryOptimisticRead();
		double value = data;// read something
		if (!sl.validate(stamp)) {
			stamp = sl.readLock();
			value = data;
		}

		try {
			while (data == 0) { // 循环，检查数据
				long ws = sl.tryConvertToWriteLock(stamp); // 将读锁转为写锁，释放readLock，若失败，返回0
				if (ws != 0L) {
					stamp = ws; // 替换票据
					data = delta; // write something

					break;
				} else {
					sl.unlockRead(stamp); // 显式释放读锁
					stamp = sl.writeLock(); // 显式进行写锁（阻塞）， 然后再通过循环再试
				}
			}
		} finally {
			sl.unlock(stamp);
		}

		return value;
	}

}
