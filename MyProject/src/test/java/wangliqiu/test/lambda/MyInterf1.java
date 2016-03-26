package wangliqiu.test.lambda;

public interface MyInterf1 {

	void m1();

	default String m2() {
		return "other!";
	}
}
