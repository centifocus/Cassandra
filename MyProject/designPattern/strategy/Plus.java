package strategy;

public class Plus extends CalculatorAssist implements Calculator {  
	  
    public int calculate(String exp) {  
        int arrayInt[] = split(exp, "\\+");  
        return arrayInt[0]+arrayInt[1];  
    }  
    
}
