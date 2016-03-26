package state;

public class ConcreteStateA implements State {

	@Override
	public void handle(String str) {

		System.out.println("A  handle ：" + str);
	}

}