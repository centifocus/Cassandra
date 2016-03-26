package command;

/**
 * 命令者类和执行者类的依赖关系通过Command解耦。
 * @author wangviviying
 *
 */
public class Test {  
	  
    public static void main(String[] args) {  
        Soldier receiver = new Soldier();  
        Command cmd = new MyCommand(receiver);  
        Commander invoker = new Commander(cmd);  
        
        invoker.action();  
    }  
    
}  
