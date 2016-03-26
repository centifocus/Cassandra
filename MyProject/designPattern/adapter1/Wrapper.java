package adapter1;

public class Wrapper implements Targetable {  
	  
    private Source source;  //内部托管类
      
    public Wrapper(Source source){  
        this.source = source;  
    }  
    
    public void method2() {  
        System.out.println("this is the targetable method!");  
    }  
  
    public void method1() {  
        source.method1();  
    }  
    
} 
