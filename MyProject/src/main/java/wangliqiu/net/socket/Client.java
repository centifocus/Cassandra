package wangliqiu.net.socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.zip.GZIPOutputStream;

/**
 * Socket的port必须为服务端ServerSocket开出的端口
 */
public class Client extends Thread {
	private static final String ip = "localhost";// 服务端ip
	private static final int port = 8821; // 服务端端口
	private static final String SAVE_PATH = "F:\\";

	public void run() {
		try {
			getMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getMessage() throws IOException, InterruptedException {
		Socket socket = new Socket(ip, port);
		DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		String fileName = SAVE_PATH + dis.readUTF(); // 因为服务端先传文件名
		DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
		ObjectOutputStream oos = new ObjectOutputStream(new GZIPOutputStream(socket.getOutputStream()));// 压缩对象传输

		byte[] buf = new byte[1024 * 10];
		int byteRead;
		while ((byteRead = dis.read(buf)) != -1) {
			dos.write(buf, 0, byteRead);
		}
		dos.flush();
		System.out.println("从服务端接受文件成功：" + fileName + "  " + socket.isClosed());

		DataModel dm = new DataModel("wangliqiu");
		oos.writeObject(dm);
		oos.flush();
		System.out.println("对象传输完成.");

		Thread.sleep(10000);
		dis.close();// 会关闭socket
		dos.close();
		socket.close();
	}

}