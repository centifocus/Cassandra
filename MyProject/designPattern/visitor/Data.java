package visitor;

public interface Data{  
	
    public void accept(Visitor visitor);  
    
    public String getStr();  
    
} 
