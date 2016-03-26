package wangliqiu.aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

public class EchoAioClient extends Thread {
	private final int port;
	private final AsynchronousSocketChannel client;
	ByteBuffer echoBuffer = ByteBuffer.allocateDirect(1024);

	public EchoAioClient(int port) throws Exception {
		this.port = port;
		this.client = AsynchronousSocketChannel.open();
	}

	public void run() {
		client.connect(new InetSocketAddress("127.0.0.1", port), null, new CompletionHandler<Void, Void>() {

			@Override
			public void completed(Void result, Void attachment) {
				try {
					echoBuffer.put("this is client data for server ".getBytes());
					echoBuffer.flip();
					client.write(echoBuffer).get();
					echoBuffer.flip();
					// System.out.println("send data to server: "
					// + Charset.forName("UTF-8").newDecoder().decode(echoBuffer).toString());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			@Override
			public void failed(Throwable exc, Void attachment) {
				System.err.println("client failed: " + exc);
			}

		});

		echoBuffer.clear();
		client.read(echoBuffer, null, new CompletionHandler<Integer, Object>() {

			@Override
			public void completed(Integer result, Object attachment) {
				System.out.println(result);
				try {
					echoBuffer.flip();
					System.out.println("receive data from answer: "
							+ Charset.forName("UTF-8").newDecoder().decode(echoBuffer).toString());
				} catch (CharacterCodingException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void failed(Throwable exc, Object attachment) {
				System.err.println("client failed: " + exc);
			}

		});

		// Wait for ever
		try {
			Thread.sleep(Integer.MAX_VALUE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
