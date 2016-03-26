package composite;

//最终节点，只有eachChild()执行最终操作。 
public class Leaf extends Component {    
	public Leaf(String name){
		super(name);
	}
    
    public void eachChild() {  
        System.out.println(name + ":do something!");  
    }  
}
