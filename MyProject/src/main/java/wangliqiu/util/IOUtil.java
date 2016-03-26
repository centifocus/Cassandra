package wangliqiu.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IOUtil {

	private static final Logger logger = LoggerFactory.getLogger(IOUtil.class);

	/**
	 * 多线程复制文件（超大文件）
	 * 
	 */
	public static boolean copyFileByThreads(String srcPath, String destPath) {
		try {
			int threadNum = Runtime.getRuntime().availableProcessors();
			File file = new File(srcPath);
			long alone = (long) Math.ceil((double) file.length() / threadNum); // 向上取整

			CountDownLatch latch = new CountDownLatch(threadNum);
			IOUtil ioUtil = new IOUtil();
			for (int i = 0; i < threadNum; i++) {
				ioUtil.new ThreadOfCopy(latch, i, alone, srcPath, destPath).start();
			}
			latch.await();// 阻塞主线程

			if (file.length() != new File(destPath).length()) {
				return false;
			}
		} catch (Exception e) {
			logger.error("", e);
		}

		return true;
	}

	class ThreadOfCopy extends Thread {
		private CountDownLatch latch;
		private long threadStartPos;
		private long threadEndPos;
		private RandomAccessFile reader;
		private RandomAccessFile writer;

		public ThreadOfCopy(CountDownLatch latch, int i, long alone, String srcPath, String destPath)
				throws IOException {
			this.latch = latch;
			reader = new RandomAccessFile(srcPath, "r");
			writer = new RandomAccessFile(destPath, "rw");
			threadStartPos = i * alone;
			if (threadStartPos + alone > reader.length()) {
				threadEndPos = reader.length();
			} else {
				threadEndPos = threadStartPos + alone;
			}
		}

		@Override
		public void run() {
			try {
				reader.seek(threadStartPos);
				writer.seek(threadStartPos);

				int buffLen = 8192;
				byte[] buffer = new byte[buffLen];
				long begin = threadStartPos;

				while (begin < threadEndPos) {
					int byteRead = 0;
					if (begin + buffLen < threadEndPos) {
						byteRead = reader.read(buffer);
					} else {
						byteRead = reader.read(buffer, 0, (int) (threadEndPos - begin));
					}
					writer.write(buffer, 0, byteRead);
					begin += byteRead;
				}
				reader.close();
				writer.close();

				latch.countDown();
			} catch (Exception e) {
				logger.error("", e);
			}
		}

	}

	/**
	 * 
	 * 
	 */
	public static boolean copyFileNio(String srcPath, String destPath) throws IOException {
		FileInputStream fis = null;
		FileChannel fci = null;
		RandomAccessFile fos = null;
		FileChannel fco = null;
		try {
			fis = new FileInputStream(srcPath);
			fci = fis.getChannel();
			fos = new RandomAccessFile(destPath, "rw");
			fco = fos.getChannel();

			// Scattering Reads
			// 若header满了，则读到body
			// ByteBuffer header = ByteBuffer.allocate(128);
			// ByteBuffer body = ByteBuffer.allocate(1024);
			// fci.read(new ByteBuffer[] {header, body});
			// Gathering Writes
			// fco.write(new ByteBuffer[] {header, body});

			MappedByteBuffer mbbi = fci.map(FileChannel.MapMode.READ_ONLY, 0, fci.size());// 模式、映射文件起始位置、映射大小
			MappedByteBuffer mbbo = fco.map(FileChannel.MapMode.READ_WRITE, 0, fci.size());
			mbbo.put(mbbi);
			// 效果同上
			// while (mbbi.hasRemaining()) {
			// mbbo.put(mbbi.get());
			// }

			Runtime.getRuntime().freeMemory();

			if (fci.size() != fco.size()) {
				return false;
			}
		} finally {
			fis.close();
			fos.close();
			fci.close();
			fco.close();
		}

		return true;
	}

	/**
	 * 复制文件(小文件)
	 */
	public static boolean copyFile(String srcPath, String destPath) {
		File oldFile = new File(srcPath);
		File newFile = new File(destPath);
		int byteRead = 0;
		try {
			if (oldFile.exists()) {
				BufferedInputStream fis = new BufferedInputStream(new FileInputStream(oldFile));
				BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(newFile));
				byte[] buffer = new byte[8192];

				while ((byteRead = fis.read(buffer, 0, buffer.length)) != -1) {
					fos.write(buffer, 0, byteRead);
					fos.flush();
				}
				fis.close();
				fos.close();

				if (oldFile.length() != newFile.length()) {
					return false;
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}

		return true;
	}

	/**
	 * 删除文件
	 */
	public static boolean delFile(String filePath) {
		if (new File(filePath).delete()) {
			logger.debug("删除文件{}成功", filePath);
		} else {
			logger.error("删除文件出错", filePath);
			return false;
		}
		return true;
	}

	/**
	 * 压缩文件从oldPath到newPath
	 */
	public static boolean Zip(String oldPath, String newPath) {
		try {
			String fileName = oldPath.substring(oldPath.lastIndexOf(File.separator) + 1, oldPath.lastIndexOf("."));

			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(oldPath));
			ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(newPath)));
			zos.putNextEntry(new ZipEntry(fileName));
			byte[] buf = new byte[1024];
			int len;

			while ((len = bis.read(buf)) != -1) {
				zos.write(buf, 0, len);
				zos.flush();
			}
			bis.close();
			zos.close();
		} catch (Exception e) {
			logger.error("压缩文件失败", e);
		}

		return true;
	}

	/**
	 * 更改文件扩展名
	 */
	public static boolean setFileNameEx(String filePath, String ex) {
		String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
		File file = new File(fileName);
		int dot = fileName.lastIndexOf('.');
		if (dot > -1) {
			if (file.exists()) {
				file.renameTo(new File(fileName.substring(0, dot) + "." + ex));
				return true;
			}
		}

		return false;
	}

	/**
	 * 获取文件至今已修改的天数
	 */
	public static Long getModifiedTime(String filePath) {
		// 系统时间
		long sysTime = new Date().getTime();
		// 文件修改时间
		long lastModified = new File(filePath).lastModified();

		return (sysTime - lastModified) / (1000 * 60 * 60 * 24);

	}

	/**
	 * 列出所有本地文件
	 */
	public static List<String> getFiles(String directoryPath) throws FileNotFoundException, IOException {
		List<String> list = new ArrayList<String>();

		File file = new File(directoryPath);
		if (file.isDirectory()) {
			String[] filelist = file.list();
			for (String str : filelist) {
				File readfile = new File(directoryPath + File.separator + str);
				if (!readfile.isDirectory()) {
					list.add(readfile.getName());
				} else {
					list.addAll(getFiles(directoryPath + File.separator + str));
				}
			}
		}

		return list;
	}

	/**
	 * 将文本内容输出到string
	 */
	public static String scan(String filePath) throws FileNotFoundException {

		StringBuffer sb = new StringBuffer();
		try (Scanner in = new Scanner(new File(filePath));) {
			while (in.hasNextLine()) {
				sb.append(in.nextLine() + "\r\n");
			}
		}

		logger.debug(sb.toString());
		return sb.toString();
	}

}
