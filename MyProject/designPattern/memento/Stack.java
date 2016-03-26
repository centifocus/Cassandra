package memento;

import java.util.ArrayList;
import java.util.EmptyStackException;

public class Stack {
	private ArrayList<Memento> pool = new ArrayList<Memento>();

	public Stack(int n) {
		pool.ensureCapacity(n);
	}

	public void clear() {
		pool.clear();
	}

	public Memento pop() {
		if (pool.isEmpty()) {
			throw new EmptyStackException();
		}
		return pool.remove(pool.size() - 1);
	}

	public void push(Memento el) {
		pool.add(el);
	}

}
