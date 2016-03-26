package wangliqiu.test.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.io.Util;
import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * FTP模式说明： FTP是基于TCP的服务，使用2个端口，一个数据端口和一个命令端口。 固定的命令端口：21 数据端口：（取决于连接模式）20或者>1024其它端口 主动FTP
 * 
 * @see 大于1024是非特权端口。
 * @see 主动方式： 客户端N端口（N>1024）连接到服务器21端口。然后客户端开始监听端口N+1，并从N端口发送FTP命令"port N+1"到服务器21端口
 *      。接着服务端端口20发起连接请求到客户端端口N+1，客户端N+1返回ACK响应到服务端20。
 * @see 针对FTP服务器前面的防火墙来说，必须允许以下通讯才能支持主动方式FTP： 1. 任何端口到FTP服务器的21端口 2. FTP服务器的21端口到大于1024的端口 3.
 *      FTP服务器的20端口到大于1024的端口 4. 大于1024端口到FTP服务器的20端口（）
 * @see 被动模式：客户端打开两个端口（N > 1024和N+1）。N连接服务器21端口，提交 PASV命令。这使得服务器会开启端口（P > 1024），并发送PORT
 *      P命令给客户端。然后客户端从端口N+1发起连接请求到服务端端口P，服务端P返回ACK响应到客户端N+1。
 * @see 对于服务器端的防火墙来说，必须允许下面的通讯才能支持被动方式的FTP: 1. 从任何端口到服务器的21端口 2. 服务器的21端口到任何大于1024的端口 3.
 *      从任何端口到服务器的大于1024端口 4. 服务器的大于1024端口到远程的大于1024的端口
 */
public class FtpTest {
	protected static Logger logger = Logger.getLogger(FtpTest.class);

	private static FTPClient ftpClient = new FTPClient();

	@Test
	public void ftpTest() throws Exception {

		ftpClient.connect("10.128.21.67", 2121);
		ftpClient.login("SHBUS8000920150814", "SHBUS8000920150814");
		ftpClient.enterLocalPassiveMode();
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream("F:\\ftp.log"));
		BufferedOutputStream bos = new BufferedOutputStream(ftpClient.storeFileStream("ssssss.ing"));
		System.out.println(ftpClient.getReplyCode());

		Util.copyStream(bis, bos);
		bis.close();
		bos.close();

		// Must call completePendingCommand() to finish.
		if (!ftpClient.completePendingCommand()) {
			ftpClient.logout();
			ftpClient.disconnect();
			System.err.println("File transfer failed 2.");
			System.exit(1);
		}

		ftpClient.logout();
		ftpClient.disconnect();

	}

}
