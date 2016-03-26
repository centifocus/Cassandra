package wangliqiu.test.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.log4j.xml.DOMConfigurator;
import org.junit.After;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class SuperTest<T> {
}

public class MyTest extends SuperTest<MyTest> {
	private final static Logger logger = LoggerFactory.getLogger(MyTest.class);

	static {
		DOMConfigurator.configure(System.getProperty("user.dir") + "/src/main/resources/log4j.xml");
	}

	public void justTest(String name, int age) {
		System.out.println(name + "====" + age);
	}

	@Test
	public void doJob() throws Exception {
		/*
		 * getStackTrace(): This method will return a zero-length array if this thread has not
		 * started. first element of the array represents the top of the stack, which is the most
		 * recent method invocation in the sequence.
		 */
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		System.out.println("methodName: " + methodName);

		ClassLoader ClassLoader = Thread.currentThread().getContextClassLoader();
		logger.info(ClassLoader.getClass().getName());

		String classPath = this.getClass().getClassLoader().getResource("").getPath();
		logger.info("class 路径  " + classPath);

		Class<?> demo = Class.forName("wangliqiu.test.reflect.MyTest");
		Field[] fields = demo.getDeclaredFields();
		/* getDeclaredClasses() 获取内部类。 */
		/*
		 * getFields()获得某个类的所有public的字段，包括父类。 
		 * getDeclaredFields()获得某个类的public、private和proteced的字段，但是不包括父类的申明字段。 
		 * 同样还有getConstructors()和getDeclaredConstructors ()；getMethods()和getDeclaredMethods()；
		 */
		for (Field field : fields) {
			field.setAccessible(true);// 强制访问
			// 属性
			System.out.println("Field: " + field.getName());
			// 权限修饰符
			int mo = field.getModifiers();
			System.out.println("Modifier: " + Modifier.toString(mo));
			// 属性类型
			Class<?> classType = field.getType();
			System.out.println("Type: " + classType.getName());
		}

		Class<?> superClass = demo.getSuperclass();// 仅获取父类
		System.out.println("getSuperclass: " + superClass);
		Type superType = demo.getGenericSuperclass();// 获取完整的父类（带有泛型参数）
		System.out.println("getGenericSuperclass: " + superType);
		// ParameterizedType参数化类型，即泛型
		// getActualTypeArguments获取类型的参数类型，泛型参数可能有多个
		Type[] types = ((ParameterizedType) superType).getActualTypeArguments();
		for (Type type : types) {
			System.out.println("getActualTypeArguments: " + type);
		}

		// 可能存在同名但不同参数个数或参数类型的方法，所以getMethod()的方法特征签名要全面。
		Method method = demo.getMethod("justTest", String.class, int.class);
		method.invoke(demo.newInstance(), "wangliqiu", 20);

		// assertEquals(0, runtime.num);

		System.out.println("com.app.ddd".replaceAll("\\.", "\\\\"));
	}

	@After
	public void ss() {
		// Bootstrap Loader会加载系统参数sun.boot.class.path指定位置中的文件
		System.out.println(System.getProperty("sun.boot.class.path"));
		// ExtClassLoader会加载系统参数java.ext.dirs指定位置中的文件
		System.out.println(System.getProperty("java.ext.dirs"));
		// AppClassLoader会加载系统参数java.class.path指定位置中的文件，即Classpath路径
		System.out.println(System.getProperty("java.class.path"));

		/*
		 * java.exe启动会尝试找到JRE安装目录的jvm.dll，接着启动JVM ，产生Bootstrap Loader，Bootstrap Loader会加载
		 * ExtClassLoader，并设定ExtClassLoader的parent为Bootstrap Loader。接着Bootstrap
		 * Loader会加载AppClassLoader，并将AppClassLoader的parent设定为Extended Loader。
		 */
		ClassLoader loader = this.getClass().getClassLoader();
		System.out.println(loader.getClass().getName());

		// This method will return null if this class was loaded by the bootstrap class loader.
		ClassLoader loaderloader = loader.getClass().getClassLoader();
		System.out.println(loaderloader);

		ClassLoader parentLoader = loader.getParent();
		System.out.println(parentLoader.getClass().getName());

		// This method will return null if this class loader's parent is the bootstrap class loader.
		ClassLoader GrandLoader = parentLoader.getParent();
		System.out.println(GrandLoader);

	}

}
