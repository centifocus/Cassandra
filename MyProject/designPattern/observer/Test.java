package observer;

/**
 * 事件驱动（观察者模式）
 *
 */
public class Test {

	public static void main(String[] args) {
		Subject sub = new MySubject();
		Event event = new MyEvent("this is myevent!!!");

		sub.add(new MyListener1());
		sub.add(new MyListener2());

		sub.notifyObservers(event);
	}

}
