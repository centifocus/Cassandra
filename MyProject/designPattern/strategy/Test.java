package strategy;

public class Test {  
	  
    public static void main(String[] args) {  
        String exp = "2+8";  
        Calculator cal = new Plus();  
         
        System.out.println(cal.calculate(exp) );  
    }  
    
} 
