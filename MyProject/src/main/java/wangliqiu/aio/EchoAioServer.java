package wangliqiu.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class EchoAioServer extends Thread {
	private final int port;

	public EchoAioServer(int port) throws InterruptedException {
		this.port = port;
	}

	public void run() {
		try {
			AsynchronousChannelGroup threadGroup = AsynchronousChannelGroup.withCachedThreadPool(
					Executors.newCachedThreadPool(), 3);// 初始等待IO线程数为3
			// 生成的server属于threadGroup
			try (AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open(threadGroup)) {
				server.setOption(StandardSocketOptions.SO_REUSEADDR, true);
				server.bind(new InetSocketAddress(port), 100);// 可挂起的连接100

				System.out.println("waiting ....");
				server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
					final ByteBuffer echoBuffer = ByteBuffer.allocateDirect(1024);

					@Override
					public void completed(AsynchronousSocketChannel result, Object attachment) {
						try {
							echoBuffer.clear();
							result.read(echoBuffer).get();
							echoBuffer.flip();
							System.out.println("receive data from client: "
									+ Charset.forName("UTF-8").newDecoder().decode(echoBuffer).toString());

							echoBuffer.clear();
							echoBuffer.put("this is server data for client".getBytes());
							echoBuffer.flip();
							result.write(echoBuffer).get();
							echoBuffer.flip();

							System.out.println(result);
						} catch (InterruptedException | ExecutionException | CharacterCodingException e) {
							System.err.println(e.toString());
						} finally {
							try {
								result.close();
								server.accept(null, this);// 递归
							} catch (Exception e) {
								System.err.println(e.toString());
							}
						}

						System.out.println("done...");
					}

					@Override
					public void failed(Throwable exc, Object attachment) {
						System.err.println("server failed: " + exc);
					}

				});

				// Wait for ever
				try {
					Thread.sleep(Integer.MAX_VALUE);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public static void main(String args[]) throws Exception {
		int port = 8039;

		new EchoAioServer(port).start();
		new EchoAioClient(port).start();
	}
}