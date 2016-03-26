package template;

public class Plus extends AbstractCalculator {  
	    
    public int plus(String exp) {  
    	int array[] = split(exp, "\\+");
    	
        return array[0] + array[1];  
    }  
    
} 