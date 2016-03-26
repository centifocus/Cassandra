package command;

public class Commander {  
    
    private Command command;  
      
    public Commander(Command command) {  
        this.command = command;  
    }  
  
    public void action(){  
        command.exe();  
    }  
}  
