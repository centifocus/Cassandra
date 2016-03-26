package adapter1;

/**
 * 对象的适配器模式，较类的适配器模式更灵活。
 *
 */
public class Test {  
	
    public static void main(String[] args) {  
        Source source = new Source();  
        Targetable target = new Wrapper(source);  
        target.method1();  
        target.method2();  
    }  
    
} 
