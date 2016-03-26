package proxy;

public class Proxy implements Sourceable {  
	  
    private Source source;  
    
    public Proxy(){ 
        this.source = new Source();  
    }  

    public void method() {            
        source.method();  
        anotherMethod();
    }  
    
    private void anotherMethod() {  
        System.out.println("another method!!!");  
    }  
    
}  
