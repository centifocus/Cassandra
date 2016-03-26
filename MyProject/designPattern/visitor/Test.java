package visitor;

/**
 * 访问者模式：将数据Data与操作Visitor分离。添加操作（匿名内部类）灵活。
 */
public class Test {

	public static void main(String[] args) {
		Data sub = new MyData();

		sub.accept(new Visitor() {
			@Override
			public void visit(Data sub) {
				System.out.println("get data：" + sub.getStr());
			}
		});
	}

}
