package facade;

/**
 * 类与类的逻辑关系由Computer类来封装
 *
 */
public class User {  
	  
    public static void main(String[] args) {  
        Computer computer = new Computer();  
        computer.startup();  
        computer.shutdown();  
    } 
    
}