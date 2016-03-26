package observer;

public class MyListener2 implements Listener<Event> {

	public void handleEvent(Event event) {
		System.out.println(this.getClass() + " : " + event);
	}

}
