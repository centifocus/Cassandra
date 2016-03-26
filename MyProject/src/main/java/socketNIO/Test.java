package socketNIO;

/**
 * NioServer线程处理accept请求，两个Processor线程处理read、write、connect请求。
 * 读队列协调NioServer线程与Processor线程的处理速度，
 * @author wangviviying
 *
 */
public class Test {  
	private static final int PORT  =  8888; // 服务器端口
	
    public static void main(String[] args) throws Exception {  
    	new NioServer(PORT).start();
    	
        for (int i = 1; i <= 5; i++) { 
        	new NioClient(i, PORT).start();
        } 
    }  
    
}  