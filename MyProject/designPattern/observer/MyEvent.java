package observer;

public class MyEvent implements Event {
	String event;

	public MyEvent(String event) {
		this.event = event;
	}

	public String toString() {
		return event;
	}
}
