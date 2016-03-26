package builder;

import java.util.ArrayList;
import java.util.List;

public class Builder {  
    
    private List<Sender> list = new ArrayList<Sender>();  
      
    public void produceFiveSender(){  
        for(int i=0; i<5; i++){  
            list.add(new MailSender());  
            list.add(new SmsSender()); 
        }  
    }  
      
    public void produceTenSender(){  
        for(int i=0; i<10; i++){  
        	list.add(new MailSender()); 
            list.add(new SmsSender());  
        }  
    }  
    
}
