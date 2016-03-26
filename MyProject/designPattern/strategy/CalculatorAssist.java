package strategy;

/**
 * 辅助类，为Plus、Minus实现类提供公共方法
 
 */
public abstract class CalculatorAssist {  
    
    public int[] split(String exp, String opt){  
        String array[] = exp.split(opt);  
        int arrayInt[] = new int[2];  
        arrayInt[0] = Integer.parseInt(array[0]);  
        arrayInt[1] = Integer.parseInt(array[1]);  
        return arrayInt;  
    } 
    
}  
