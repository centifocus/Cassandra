package wangliqiu.util;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * s
 * 
 * @TODO dd
 *
 */
public class MyWatcher {

	private static final Logger logger = LoggerFactory.getLogger(MyWatcher.class);

	public static void main(String[] args) throws IOException {
		Path dir = Paths.get(".");// "F:\\");
		Path realDir = dir.toRealPath(LinkOption.NOFOLLOW_LINKS);// 去除如..这种父目录标识符
		Path srcDir = realDir.resolve("src");// 定位目录src
		logger.info(dir.toString() + "   " + realDir.toString() + "   " + srcDir.toString());
		for (Path name : srcDir) {
			logger.info(name.toString());
		}
		Files.createDirectories(srcDir);

		try {
			WatchService watcher = FileSystems.getDefault().newWatchService();
			dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE,
					StandardWatchEventKinds.ENTRY_MODIFY);

			while (true) {
				WatchKey watchKey = watcher.take();
				for (WatchEvent<?> event : watchKey.pollEvents()) {// pollEvents()非阻塞
					Kind<?> kind = event.kind();
					if (kind == StandardWatchEventKinds.OVERFLOW) {// 事件可能lost or discarded
						continue;
					}
					System.out.println(kind.name() + " : " + event.context().toString()); // event.context()返回监控路径的相对路径
				}
				// Events detected while the key is in the signalled state are queued but do not
				// cause the key to be re-queued for retrieval from the watch service.
				if (!watchKey.reset()) {
					break;
				}
			}

		} catch (Exception e) {
			logger.error("", e);
		}
	}
}
