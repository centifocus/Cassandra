package wangliqiu.util;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.util.EnumSet;

public class MyFileVisitor {

	public static void main(String[] args) throws IOException {
		workFilePath();
	}

	private static void workFilePath() throws IOException {
		Path listDir = Paths.get("."); // starting dir

		EnumSet<FileVisitOption> opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS); // 如..这种相对路径显示
		try {
			Files.walkFileTree(listDir, opts, Integer.MAX_VALUE, new ListTree());// 深度优先遍历
		} catch (IOException e) {
			System.err.println(e);
		}
	}

}

class ListTree extends SimpleFileVisitor<Path> {// 递归遍历文件类
	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
		System.out.println("after visit: " + dir.toString());
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) {
		System.out.println(exc);
		return FileVisitResult.CONTINUE;
	}
}