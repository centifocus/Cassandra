package socketNIO;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class NioClient extends Thread {
	private int PORT  =  8888; // 服务器端口
	private String threadName;
	private int i;
	private Socket socket;
	
	public NioClient(int i, int port){
		this.PORT = port;
		this.i = i;
		this.threadName = "Thread_" + i;;
	}
	
	public void run() {
		try {
			//getLocalHost() 获得本地InetAddress	      
			socket = new Socket(InetAddress.getLocalHost(), PORT, InetAddress.getLocalHost(), 7777+i); //获得服务端的socket连接
			
	        InputStream  inStream1 = socket.getInputStream();
	        OutputStream outStream1 = socket.getOutputStream();
	        
	        // 输出
	        PrintWriter out = new PrintWriter(outStream1, true);
	        out.print("clientData:" + this.threadName);
	        out.flush();
	        socket.shutdownOutput();
	        
	        // 输入
	        Scanner in = new Scanner(inStream1);
	        StringBuilder sb = new StringBuilder();
	        while(in.hasNextLine()){
	            sb.append( in.nextLine());
	        }
	        System.out.println(this.threadName + " response = " + sb.toString() );
	        
	    } catch (IOException e) {
				
		}
	
    }
}