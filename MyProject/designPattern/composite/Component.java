package composite;



public abstract class Component {  
    String name;  
    
    public Component(String name){
    	this.name = name;
    }

    public abstract void eachChild();  //遍历的关键，不断迭代
} 
