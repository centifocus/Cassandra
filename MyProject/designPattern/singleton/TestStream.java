package singleton;

public class TestStream {
	
	private TestStream(){	
		//、、、、、、、、该构造方法内容可以写在初始化的static块中,相同类的不同实例只执行一次。
	}   
	     
	//饿汉式单例类//在类初始化时，已经自行实例化//缺点是无法完成延迟加载
// private static TestStream instance = new TestStream();	
//	public static TestStream getInstance(){
//        return instance;
// }
	
	 //必须synchronized，以防在多线程时被重复实例//缺点是多线程性能差
//	private static TestStream ts1=null;
//	public static synchronized TestStream getTest(){ 
//      if(ts1==null){
//        	ts1=new TestStream();
//	    }
//	    return ts1;
//	}
	
	//JVM内部的机制能够保证当一个类被加载的时候，这个类的加载过程是线程互斥的。
	private static class SingletonHolder{//通过静态内部类来托管单例
        private static TestStream instance=new TestStream();
    }
    private static TestStream getInstance(){
        return SingletonHolder.instance;
    }
    
 
    
    
  }