package observer;

import java.util.Collection;
import java.util.LinkedList;

public abstract class AbstractSubject implements Subject {

	private Collection<Listener<? extends Event>> coll = new LinkedList<>(); // 观察者集合

	public void add(Listener<? extends Event> observer) {
		coll.add(observer);
	}

	public void remove(Listener<? extends Event> observer) {
		coll.remove(observer);
	}

	public <T extends Event> void notifyObservers(T event) {
		for (Listener listener : coll) {
			listener.handleEvent(event);
		}
	}
}
