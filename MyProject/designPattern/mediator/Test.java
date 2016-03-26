package mediator;

/**
 * User1、User2在中介类中实例化，降低类类之间的耦合。
 * 类类之间的关系及调度交给MyMediator。
 * @author wangviviying
 *
 */
public class Test {  
	  
    public static void main(String[] args) {  
        Mediator mediator = new MyMediator();  
        mediator.createUser();  
        mediator.workAll();  
    }  
    
} 
