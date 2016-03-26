package chainOfResponsibility;

/**
 * @see 适用场景： 在不明确指定接收者的情况下，向多个对象中的一个提交一个请求； 处理一个请求的对象集合应被动态指定。
 * 
 * @see AbstractHandler为具体类MyHandler提供公共属性和方法。
 *
 */
public class Test {

	public static void main(String[] args) {
		// 线性链
		// MyHandler h1 = new MyHandler("h1");
		// MyHandler h2 = new MyHandler("h2");
		// MyHandler h3 = new MyHandler("h3");
		//
		// h1.setHandler(h2);
		// h2.setHandler(h3);
		//
		// h1.operator();

		// 循环链
		// MyHandler h1 = new MyHandler("h1");
		// MyHandler h2 = new MyHandler("h2");
		// MyHandler h3 = new MyHandler("h3");
		//
		// h1.setHandler(h2);
		// h2.setHandler(h3);
		// h3.setHandler(h1);
		//
		// h1.operator();

		// 树形链
	}

}
