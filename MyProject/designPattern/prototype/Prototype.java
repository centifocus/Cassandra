package prototype;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 序列化用于将字节流的对象进行校验。
 * 序列化的实现：将需要被序列化的类实现Serializable接口，该接口没有方法。
 */
public class Prototype implements Cloneable, Serializable {  
	  
    private static final long serialVersionUID = 1L;  
    public MyObject myObject;
 
    /* 浅复制 */  
    @Override
    public Object clone() throws CloneNotSupportedException {  
        Object proto = super.clone();  
        return proto;  
    }  
    
    /* 深复制 */  
    public Object deepClone(Object oldObj) throws IOException, ClassNotFoundException {  
  
        /* 写入当前对象的二进制流 */  
        ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        ObjectOutputStream oos = new ObjectOutputStream(bos);  
        oos.writeObject(oldObj);  
  
        /* 读出二进制流产生的新对象 */  
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());  
        ObjectInputStream ois = new ObjectInputStream(bis);  
        return ois.readObject();  
    }  
   
    public static void main(String[] argStrings) throws CloneNotSupportedException, ClassNotFoundException, IOException {
		Prototype prototype = new Prototype();		
		prototype.myObject = new MyObject();	
		
		Prototype prototype2 = (Prototype) prototype.clone();
		System.out.println(prototype2.myObject == prototype.myObject);
				
		Prototype prototype3 = (Prototype) prototype.deepClone(prototype);
		System.out.println(prototype3.myObject == prototype.myObject);		
	}
    
}  
  
class MyObject implements Serializable {  
    private static final long serialVersionUID = 1L;   
} 
