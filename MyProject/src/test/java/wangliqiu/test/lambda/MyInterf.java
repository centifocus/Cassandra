package wangliqiu.test.lambda;

public interface MyInterf {

	void m1();

	default String m2() {
		return "default method in Interface!";
	}

	static String m3() {
		return "static method in Interface!";
	}

}
