package composite;

import java.util.ArrayList;
import java.util.List;

//中间节点 //容器
public class Composite extends Component {    
	// 用来保存节点  
    List<Component> list = new ArrayList<Component>();  
    
    public Composite(String name){
		super(name);
	}

    //节点的add和remove需要容器自己定义
    public void add(Component c) {  
        list.add(c);  
    }
    public void remove(Component c) {  
        list.remove(c);  
    }  

    // 遍历子节点 
    public void eachChild() {  
        System.out.println(name + ":do something!");  
        
        for (Component c : list) {  
            c.eachChild();  
        }  
    }  
}
