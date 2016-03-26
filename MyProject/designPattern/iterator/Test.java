package iterator;

/**
 * 
 * 接口定义接口。
 * @author wangviviying
 *
 */
public class Test {  
	  
    public static void main(String[] args) {  
        Collection collection = new MyCollection();  
        Iterator it = collection.iterator();  
          
        while(it.hasNext()){  
            System.out.println(it.next());  
        }  
    }  
    
} 
