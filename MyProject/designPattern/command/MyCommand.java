package command;

public class MyCommand implements Command {  
	  
    private Soldier receiver;  
      
    public MyCommand(Soldier receiver) {  
        this.receiver = receiver;  
    }  
  
    public void exe() {  
        receiver.action();  
    }  
    
}
