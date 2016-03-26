package adapter2;

/**
 * 接口的适配器模式：借助于一个抽象类，该抽象类实现了该接口的所有的方法，
 * 而我们不和原始的接口打交道，只和该抽象类取得联系，所以我们写一个类，继承该抽象类，重写我们需要的方法就行。 
 *	SourceSub1适配了接口，SourceSub2也适配了接口（只实现了部分接口方法）。
 */
public class Test {  
	  
    public static void main(String[] args) {  
        Sourceable source1 = new SourceSub1();  
        Sourceable source2 = new SourceSub2();  
          
        source1.method1();  
        source1.method2();  
        source2.method1();  
        source2.method2();  
    }
    
} 
