package template;

/**
 * 一次性实现一个算法的不变的部分, 并将可变的行为留给子类来实现.
 * 
 * @see 模板模式和策略模式功能上没什么不同，不过策略模式更松耦合。
 *
 */
public class Test {

	public static void main(String[] args) {
		String exp = "8+8";
		AbstractCalculator cal = new Plus();

		System.out.println(cal.calculate(exp));
	}

}
