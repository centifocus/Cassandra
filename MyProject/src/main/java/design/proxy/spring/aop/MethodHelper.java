package design.proxy.spring.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;

/**
 * Object[] getArgs：返回目标方法的参数
 * 
 * Signature getSignature：返回目标方法的签名 与java.lang.reflect.Member.类似
 * 
 * Object getTarget：返回被织入增强处理的目标对象
 * 
 * Object getThis：返回AOP框架为目标对象生成的代理对象
 * 
 * 注意：当使用@Around处理时，我们需要将第一个参数定义为ProceedingJoinPoint类型，该类是JoinPoint的子类。
 */

@Aspect
@Order(value = 2)
public class MethodHelper {

	/**
	 * 同一个切面类里的两个相同类型的增强处理在同一个连接点被织入时，Spring AOP将以随机的顺序来织入这两个增强处理。
	 */

	/**
	 * 需要使用增强处理阻止目标方法的执行，或者需要改变目标方法的参数和执行后的返回值时，就只能使用@Around了。
	 */
	// 环绕通知 ProceedingJoinPoint.proceed()将切点sleep方法代替了。
	// args()可以指定切点方法的入参传递到切面方法的入参中String name //..表示0个或多个
	@Around("PointcutDef.point() && args(name, ..)")
	public Object process(ProceedingJoinPoint point, String name) throws Throwable {
		System.out.println("name   " + name);
		System.out.println("@Around：执行目标方法之前..参数..." + Arrays.toString(point.getArgs()));
		Object[] args = point.getArgs();
		args[0] = "改变后的参数AAAAAAA";

		// 用改变后的参数执行目标方法
		Object returnValue = point.proceed(args);
		System.out.println("@Around：执行目标方法之后...");
		System.out.println("@Around：被织入的目标对象为：" + point.getTarget());
		return returnValue + "<<<>>>";

	}

	@Before("PointcutDef.point()")
	public void permissionCheck(JoinPoint point) {
		System.out.println("@Before：模拟权限检查...");

		System.out.println("@Before：参数为：" + Arrays.toString(point.getArgs()));
	}

	@After("PointcutDef.point()")
	public void releaseResource(JoinPoint point) {
		System.out.println("@After：模拟释放资源...");

		System.out.println("@After：参数为：" + Arrays.toString(point.getArgs()));
	}

	// @AfterReturning只在目标方法成功执行后才会执行，但@After都会。
	@AfterReturning(pointcut = "PointcutDef.point()", returning = "returnValue")
	public void log(JoinPoint point, Object returnValue) {
		System.out.println("@AfterReturning：模拟日志记录...");

		System.out.println("@AfterReturning：目标方法为：" + point.getSignature().getDeclaringType().getName() + "."
				+ point.getSignature().getName());
		System.out.println("@AfterReturning：参数为：" + Arrays.toString(point.getArgs()));
		System.out.println("@AfterReturning：返回值为：" + returnValue);
		System.out.println("@AfterReturning：被织入的目标对象为：" + point.getTarget());
	}

}