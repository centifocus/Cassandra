package wangliqiu.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/** 
 * CyclicBarrier：N个线程执行到await()就等待。 由最后一个线程来执行后续工作。直到后续工作完成，N个线程才能继续执行。
 * 调用reset()后可以重用。
 */  
public class MyCyclicBarrier {  
	
	private final static int SIZE = 10;
  
    public static void main(String[] args) throws InterruptedException {      	
        ExecutorService exec = Executors.newCachedThreadPool();   

        //屏障操作由最后一个进入 barrier 的线程执行。
        final CyclicBarrier barrier = new CyclicBarrier(SIZE, new Runnable() {            
            public void run() {              
                try {
                	System.out.println(Thread.currentThread().getName() + ":begin another work!!!");  
					Thread.sleep(1000l);
					System.out.println(Thread.currentThread().getName() + ": another work done!!");
				} catch (InterruptedException e) {
					
				}
            }  
        });                 
          
        for (int i = 0; i < SIZE; i++) {  
            exec.execute(new Runnable() {   
                public void run() {  
                    System.out.println(Thread.currentThread().getName() + ":Go");                    
                    try {
                    	Thread.sleep((long) (2000 * Math.random()));
						System.out.println(Thread.currentThread().getName() + ":await!!!"); 
                        barrier.await();  
                        System.out.println(Thread.currentThread().getName() + ": do something");
                    } catch (InterruptedException e) {  
                      
                    } catch (BrokenBarrierException e) {  
                         
                    }  
                }  
            });    
        } 
        
        exec.shutdown();                    
    }  
  
} 
