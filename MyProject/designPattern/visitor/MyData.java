package visitor;

public class MyData implements Data {  
	 
	private String str = "A";
	
    public void accept(Visitor visitor) {  
        visitor.visit(this);  
    }  
  
    public String getStr() {  
        return str;  
    }  
    
} 
