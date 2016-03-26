package wangliqiu.net.socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.zip.GZIPInputStream;

/**
 * 传统的socket：一个socket需要一个端口，即大量的客户端socket需要大量的服务器端口。
 */
public class Server extends Thread {
	private static final int PORT = 8821;
	private static final String FILE_PATH = "D:\\Photos\\ito misaki\\itomisaki image\\nv 1.jpg";

	public void run() {
		try {
			transfer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void transfer() throws IOException, InterruptedException, ClassNotFoundException {
		ServerSocket serverSocket = new ServerSocket(PORT); // 建立服务端socket
		Socket socket = serverSocket.accept(); // 获得客户端socket//阻塞
		DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(FILE_PATH)));
		DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		// ObjectInputStream ois = new ObjectInputStream(new
		// GZIPInputStream(socket.getInputStream()));// 解压对象

		// UTF-8输入流的前2个字节是数据的长度
		dos.writeUTF(new File(FILE_PATH).getName()); // 写入文件名

		byte[] buf = new byte[1024 * 10];
		int byteRead;
		while ((byteRead = dis.read(buf)) != -1) {
			dos.write(buf, 0, byteRead);
		}
		dos.flush();
		System.out.println("文件传输完成。");

		// DataModel dm = (DataModel) ois.readObject();
		// System.out.println("对象接收：" + dm.name);

		dis.close();
		dos.close();
		if (socket.isClosed()) {
			serverSocket.close();
		}

	}

}
