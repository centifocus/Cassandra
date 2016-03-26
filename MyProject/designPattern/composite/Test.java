package composite;

  
public class Test {  
      
    public static void main(String[] args) {      
        // 构造根节点  
        Composite root = new Composite("根节点");  
        // 构造左节点  
        Composite left = new Composite("左节点");  
        // 构造右节点 
        Composite right = new Composite("右节点");  
        
        Leaf leaf1 = new Leaf("右-子节点1");  
        Leaf leaf2 = new Leaf("右-子节点2");  
        right.add(leaf1);  
        right.add(leaf2);  
  
        // 左右节点加入 根节点  
        root.add(left);  
        root.add(right);  
        // 遍历
        root.eachChild();  
    }  
} 





