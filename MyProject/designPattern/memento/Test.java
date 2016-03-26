package memento;

/**
 * 原始类，备份类，保存类（保存备份类）。
 * @author wangviviying
 *
 */
public class Test {  
	  
    public static void main(String[] args) {
      
        Original origi = new Original("A！！！");    
        Storage storage = new Storage(origi.createMemento());    // 创建备份
        
        System.out.println("初始化状态为：" + origi.getValue());  
        // 修改原始类的状态 
        origi.setValue("B！！！");  
        System.out.println("修改后的状态为：" + origi.getValue());  
  
        // 恢复原始类的状态  
        origi.restoreMemento(storage.getMemento());  
        System.out.println("恢复后的状态为：" + origi.getValue());  
    }  
    
}
