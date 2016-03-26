package socketNIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * ServerSocketChannel的register是针对所有客户端的SocketChannel。 SocketChannel的register是针对自身。
 * 一个selectionKey对应一个客户端SocketChannel。
 * 
 * @author wangviviying
 *
 */
public class NioServer extends Thread {

	private static final int SocketBufSize = 1024 * 8;
	private ProcessorGroup processorGroup;
	private ServerSocketChannel serverChannel;
	private ServerSocket serverSocket;
	private Selector selector;

	public NioServer(int port) throws IOException {
		this.serverChannel = ServerSocketChannel.open();
		this.serverChannel.configureBlocking(false); // 设置为非阻塞，即ServerSocketChannel.accept() 立即返回
		this.serverSocket = this.serverChannel.socket();// 获得服务端socket
		this.serverSocket.setReuseAddress(false);// 禁用 SO_REUSEADDR 套接字选项，对超时连接状态的socket无法bind
		this.serverSocket.setReceiveBufferSize(SocketBufSize);
		this.serverSocket.bind(new InetSocketAddress(port));// 绑定服务端的IP地址、端口号

		this.selector = Selector.open();
		this.serverChannel.register(selector, SelectionKey.OP_ACCEPT);// 向给定的选择器注册此通道，返回一个选择键。

		this.processorGroup = ProcessorGroup.getInstance();
	}

	public void run() {
		try {
			for (;;) {
				selector.select();// 阻塞方法，至少有一个channel被selected。
				Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();
				while (keyIter.hasNext()) {
					SelectionKey key = keyIter.next();

					if (key.isValid() && key.isAcceptable()) {
						// key.channel()生成的channel包含了客户端socket信息。
						SocketChannel socketChannel = ((ServerSocketChannel) key.channel()).accept();
						this.processorGroup.nextProcessor().addChannel(socketChannel);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
