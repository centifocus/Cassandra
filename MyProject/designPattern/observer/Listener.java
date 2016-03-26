package observer;

public interface Listener<T extends Event> {

	public void handleEvent(T event);

}
