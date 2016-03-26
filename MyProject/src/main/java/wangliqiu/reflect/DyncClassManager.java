package wangliqiu.reflect;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

import org.apache.log4j.Logger;

/**
 * 动态编译
 *
 */
public class DyncClassManager {
	protected static final Logger logger = Logger.getLogger(DyncClassManager.class);
	private static Lock lock = new ReentrantLock();

	private DyncClassManager() {

	}

	private static class SingletonHolder {
		private static DyncClassManager instance = new DyncClassManager();
	}

	public static DyncClassManager getInstance() {
		return SingletonHolder.instance;
	}

	/**
	 * 列出所有本地文件
	 */
	public static List<String> getFiles(String directoryPath) throws FileNotFoundException, IOException {
		List<String> list = new ArrayList<>();

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
	 * 编译+加载class， coll为包名+类名的集合
	 */
	public <T> void loadClass(String srcPath, Collection<String> coll) throws Exception {
		lock.lock();
		try {
			JavaCompiler complier = ToolProvider.getSystemJavaCompiler();
			try (StandardJavaFileManager sjf = complier.getStandardFileManager(null, null, null);) {// null为默认
				List<String> fileList = new ArrayList<>();
				for (String name : coll) {
					if (!fileList.contains(srcPath + name + ".java")) {// 过滤重复java文件
						fileList.add(srcPath + name + ".java");
					}
				}
				Iterable<? extends JavaFileObject> jfo = sjf.getJavaFileObjectsFromStrings(fileList);
				// Iterable<? extends JavaFileObject> jfo = sjf.getJavaFileObjects(javaFile);
				StringWriter sw = new StringWriter();
				CompilationTask task = complier.getTask(sw, sjf, null, null, null, jfo); // CompilationTask是future接口
				task.call(); // 执行编译，只能调用一次。

				logger.info("编译信息:\n" + sw.toString());
			}

			URL[] urls = new URL[] { new File(srcPath).toURI().toURL() };
			try (URLClassLoader loader = new URLClassLoader(urls);) {
				for (String name : coll) {
					Class<?> c = loader.loadClass(name); // 注意不带后缀名，但要加包名
					// Object iDyncClass = c.newInstance();

					logger.info(name + "载入成功!");
				}
			}
		} finally {
			lock.unlock();
		}

	}

}
