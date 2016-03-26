package wangliqiu.thread;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
  
/**   
 * Semaphore可以看成是个通行证，线程要想执行必须先拿到通行证，拿不到通行证，就会进入等待状态。 
 */  
public class MySemaphore {  
    /** 
     * 模拟资源池，只为池发放2个通行证，即同时只允许2个线程获得池中的资源。 
     */  
	public static void main(String[] args) {  
	    MySemaphore.test();  
	}  
	 
    public static class Pool {          
        ArrayList<String> pool = new ArrayList<String>();       
        Semaphore pass = new Semaphore(0, true);  //初始定义0个通行证	  //true为先到先得
        Lock lock = new ReentrantLock();  
        
        public Pool(int size) {    
            for (int i = 0; i < size; i++) {  
                pool.add("Resource " + i);  
            }        
        }  
  
        public String getPass() throws InterruptedException {                   
            pass.acquire();  	//阻塞方法
            System.out.println("Got a pass");  
            return getResource();  
        }  
  
        public void putPass(String resource) {              
            pass.release();
            System.out.println("Released a pass");  
            releaseResource(resource);  
        }  
  
        private String getResource() {  
            lock.lock();  
            String str = pool.remove(0);  
            System.out.println(str + " 被取走");  
            lock.unlock();  
            return str;  
        }  
  
        private void releaseResource(String str) {  
            lock.lock();
            pool.add(str);
            System.out.println(str + " 被归还");  
            lock.unlock();  
        }   
    }  
      
    public static void test() {          
        final Pool pool = new Pool(10); 		// 准备10个资源的资源池   
        pool.pass.release(2);		//发放2个通行证
        
        Runnable worker = new Runnable() {  
            public void run() {  
            	String resource = null;
                try {                      
                    resource = pool.getPass();    	//取得通行证，取得resource                    
                    work(resource);
                    pool.putPass(resource);  		//释放通行证，归还resource  
                } catch (InterruptedException ex) {  
                	
                }    
            } 
            
            public void work(String resource) throws InterruptedException {
            	Thread.sleep(500);
            	System.out.println("Do something with " + resource); 
			}
        }; 
        
        
        ExecutorService threadPool = Executors.newCachedThreadPool();  
        for (int i = 0; i < 10; i++) {  	// 启动10个任务  
        	threadPool.submit(worker);  
        }  
        threadPool.shutdown();  
        
    }   
      
   
} 
