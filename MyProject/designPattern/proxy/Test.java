package proxy;

/**
 * 代理模式对于客户来说是直接调用Proxy，屏蔽了Source。
 * @author wangviviying
 *
 */
public class Test {  
	  
    public static void main(String[] args) {  
        Sourceable source = new Proxy();  
        source.method();  
    }  
  
}  
