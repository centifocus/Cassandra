package flyweight;

import java.util.Hashtable;

public class FlyweightFactory{  

	private Hashtable<Object, Flyweight> flyweights = new Hashtable<Object, Flyweight>();//保存对象
	
	//如果hashtable没有所需对象，实例化一个；若有就直接返回该对象
	public Flyweight getFlyWeight(Object obj){ 
		Flyweight flyweight = flyweights.get(obj);
		if(flyweight == null){  			
			flyweight = new ConcreteFlyweight((String)obj);  
			flyweights.put(obj, flyweight);
		}  
		return flyweight;
	}  
	
	public int getFlyweightSize(){  
		return flyweights.size();  
	}
	
}
