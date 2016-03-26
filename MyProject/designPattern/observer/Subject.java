package observer;

public interface Subject {

	/* 增加观察者 */
	public void add(Listener<? extends Event> observer);

	/* 删除观察者 */
	public void remove(Listener<? extends Event> observer);

	/* 通知所有的观察者 */
	public <T extends Event> void notifyObservers(T event);

}
