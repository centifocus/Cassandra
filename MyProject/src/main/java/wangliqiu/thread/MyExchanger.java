package wangliqiu.thread;

import java.util.concurrent.Exchanger;

/**
 * Exchanger提供了一个同步点，在这个同步点，一对线程可以交换数据。
 */
public class MyExchanger { 
	
    public static void main(String args[]) {  
          Exchanger<String> exchanger = new Exchanger<String>();  

          new UseString(exchanger);  
          new MakeString(exchanger);  
    }  
}  

class MakeString implements Runnable {  	
    Exchanger<String> exchanger;  
    String str;

    public MakeString(Exchanger<String> exchanger) {  
    	this.exchanger = exchanger;      	

    	new Thread(this).start();  
    }  

    @Override
    public void run() {      	
    	for (int i = 5; i >0; i--) {         		
    		try {  
    			str = exchanger.exchange(String.valueOf(i));  
    			System.out.println(Thread.currentThread().getName() + "       str: " + str + "    i:" + i);
    		}catch (InterruptedException exc) {  
    			  
    		}  
    	}  
    }  
    
}  

class UseString implements Runnable {  	
    Exchanger<String> exchanger;  
    String str;  

    public UseString(Exchanger<String> exchanger) {  
    	this.exchanger = exchanger;  
    	
    	new Thread(this).start();  
    }  

    @Override
    public void run() { 
    	for (int i = 0; i < 5; i++) { 	
			try {
				str = exchanger.exchange(String.valueOf(i));
				System.out.println(Thread.currentThread().getName() + "        get: " + str + "    i:" + i); 
			} catch (InterruptedException e) {
			
			}			
    	}  
    }  
    
} 
    
