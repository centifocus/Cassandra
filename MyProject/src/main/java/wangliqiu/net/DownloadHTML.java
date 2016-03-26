package wangliqiu.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * 下载网页文本
 */
public class DownloadHTML {

	private Charset charset = Charset.forName("UTF-8");
	private SocketChannel channel;

	public void getHTMLContent() throws IOException {
		InetSocketAddress socketAddress = new InetSocketAddress("blog.csdn.net", 8080);
		channel = SocketChannel.open(socketAddress);// 相当与SocketChannel.open().connect(socketAddress);

		// SocketChannel 是双向的，可读、可写
		channel.write(charset.encode("/cuihaiyang/article/details/6742869"));

		ByteBuffer buffer = ByteBuffer.allocate(1024);
		while (channel.read(buffer) != -1) {
			/*
			 * The limit is set to the current position and then the position is set to zero. If the
			 * mark is defined then it is discarded.
			 */
			buffer.flip();
			System.out.println(charset.decode(buffer));
			/*
			 * The position is set to zero, the limit is set to the capacity, and the mark is
			 * discarded.
			 */
			buffer.clear();
		}
		channel.close();

	}

	public static void main(String[] args) throws IOException {

		new DownloadHTML().getHTMLContent();

	}

}
