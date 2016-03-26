package state;

public class ConcreteStateB implements State {

	@Override
	public void handle(String str) {

		System.out.println("B handle ：" + str);
	}

}