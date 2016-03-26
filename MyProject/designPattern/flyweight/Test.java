package flyweight;


/**
 * 在java中，数据库连接池、线程池、字符串池等即是用享元模式的应用。

 */
public class Test{  
	
	FlyweightFactory factory = new FlyweightFactory();   
	Flyweight fly1;  
	Flyweight fly2;  
	Flyweight fly3;  
	Flyweight fly4;  
	Flyweight fly5;  
	Flyweight fly6;   
	
	public void showFlyweight(){  
		fly1 = factory.getFlyWeight("Google"); 
		fly1.operation();
		fly2 = factory.getFlyWeight("Qutr");    
		fly2.operation();  
		fly3 = factory.getFlyWeight("Google"); 
		fly3.operation();  
		fly4 = factory.getFlyWeight("Google"); 
		fly4.operation(); 
		fly5 = factory.getFlyWeight("Google"); 
		fly5.operation();  
		fly6 = factory.getFlyWeight("Google"); 
		fly6.operation();  		
		System.out.println("object Size = " + factory.getFlyweightSize());  
	}  

	public static void main(String[] args){  
		Test fp = new Test();  
		fp.showFlyweight();  
	}  
	
}  
