package design.proxy.spring.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;

/**
 * 优先级高的切面类里的增强处理的优先级总是比优先级低的切面类中的增强处理的优先级高。
 * 
 * 所以给定的两个Before增强处理中，优先级高的那个会先执行
 * 
 * 所以给定的两个After增强处理中，优先级高的那个会后执行
 * 
 * @see 在本例中，切点JoinPoint会在MethodHelperOther@Before执行前就切了（织入），所以得到的参数都是wangliqiu，不是"改变后的参数AAAAAAA";
 */
@Aspect
// 表示切面优先级
@Order(value = 1)
public class MethodHelperOther {

	@Before("PointcutDef.point()")
	public void permissionCheck(JoinPoint point) {
		System.out.println(this.getClass() + "   @Before：模拟权限检查...");

		System.out.println(this.getClass() + "   @Before：参数为：" + Arrays.toString(point.getArgs()));
	}

	@After("PointcutDef.point()")
	public void releaseResource(JoinPoint point) {
		System.out.println(this.getClass() + "  @After：模拟释放资源...");

		System.out.println(this.getClass() + "  @After：参数为：" + Arrays.toString(point.getArgs()));
	}

}