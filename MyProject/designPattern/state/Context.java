package state;

/**
 * 持有一个State类型的对象
 */
public class Context {
	private State state;

	public void setState(State state) {
		this.state = state;
	}

	/**
	 * 用户感兴趣的接口方法
	 */
	public void request(String str) {
		state.handle(str);
	}
}
