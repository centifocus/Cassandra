package socketNIO;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DTask {

	private static final String CODE  =  "gb2312"; 
	public static final int DEFAULT_BUFFER_SIZE  = 1024;
	public static final int DEFAULT_STORAGE_SIZE = 2048;
	
	protected Processor singleProcessor;
	protected SocketChannel socketChannel;
	protected ByteBuffer storage = ByteBuffer.allocate(DEFAULT_STORAGE_SIZE);
	protected ByteBuffer buffer = ByteBuffer.allocate(DEFAULT_BUFFER_SIZE);
	protected Queue<ByteBuffer> waitForSendQueue;
	  
	//构造方法
	public DTask(Processor singleProcessor, SocketChannel socketChannel){
		this.socketChannel = socketChannel;
		this.singleProcessor = singleProcessor;
		this.waitForSendQueue = new ConcurrentLinkedQueue<ByteBuffer>();
	}
	 
	public void onDataRecieve() throws IOException{
    	buffer.clear();
    	int dataLength = socketChannel.read(buffer);
        if (dataLength != -1) {
        	buffer.flip();//反转缓冲区 (将缓冲区准备为数据传出状态)//返回本身
        	// 将buffer内容解码 
        	String receivedString = Charset.forName(CODE).decode(buffer).toString();
        	
            System.out.println("SocketChannel=" + socketChannel.socket().getInetAddress().getHostAddress() 
            		+":"+socketChannel.socket().getPort() + ",recieve data:"+ receivedString);
            
        }else {
        	this.socketChannel.close();
        	this.socketChannel.socket().close();
		}
        
	 }
	
	public void onDataSend() throws IOException{
		String sendString = "serverData:OK!!!";  
    	storage = ByteBuffer.wrap(sendString.getBytes(CODE));//包装字节数组生成缓冲区
    	long wLen = socketChannel.write(storage);
    	long bLen = buffer.limit();
    	if(wLen != bLen){
			System.out.println("server:Send data lost."+System.currentTimeMillis());
			
		}
	}
	 
}
