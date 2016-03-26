package wangliqiu.net.socket;

import java.io.IOException;

import org.junit.Test;

import wangliqiu.net.socket.Client;
import wangliqiu.net.socket.Server;

public class TestOfServerClient {

	@Test
	public void execute() throws IOException, InterruptedException {
		// server传文件给client，client穿对象给server
		new Server().start();
		new Client().start();

		Thread.sleep(6000);

	}
}
