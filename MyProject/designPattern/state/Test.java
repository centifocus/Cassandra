package state;

/**
 * 通过改变状态来获得不同的行为。
 *
 */
public class Test {

	public static void main(String[] args) {
		Context context = new Context();
		State state = new ConcreteStateB();
		context.setState(state);

		// 用户感兴趣的操作
		context.request("test");
	}

}
