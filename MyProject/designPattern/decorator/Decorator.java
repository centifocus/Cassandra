package decorator;

public class Decorator implements Sourceable {  
	  
    private Sourceable source;  
      
    public Decorator(Sourceable source){  
        this.source = source;  
    }  
    
    public void method() {  
        
        source.method();  
        System.out.println("another method");  //do something
    }  
    
} 
