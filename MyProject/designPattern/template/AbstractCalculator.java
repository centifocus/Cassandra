package template;

public abstract class AbstractCalculator {  
    
    //final主方法，实现对本类其它方法的调用
    public final int calculate(String exp){  
        
        return plus(exp);  
    }  
      
    //由子类来实现算法  
    public abstract int plus(String exp);  
      
    public int[] split(String exp, String opt){  
        String array[] = exp.split(opt);  
        int arrayInt[] = new int[2];  
        arrayInt[0] = Integer.parseInt(array[0]);  
        arrayInt[1] = Integer.parseInt(array[1]);  
        return arrayInt;  
    }  
    
} 
