package observer;

public class MyListener1 implements Listener<Event> {

	public void handleEvent(Event event) {
		System.out.println(this.getClass() + " : " + event);
	}

}
