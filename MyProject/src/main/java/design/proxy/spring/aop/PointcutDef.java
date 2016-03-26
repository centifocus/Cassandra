package design.proxy.spring.aop;

import org.aspectj.lang.annotation.Pointcut;

/**
 * 定义切入点
 * 
 * 用空方法最简便
 */

public class PointcutDef {

	/**
	 * 切入点指示符：execution(modifier-pattern? ret-type-pattern declaring-type-pattern?
	 * name-pattern(param-pattern) throws-pattern?)
	 */

	@Pointcut("execution(* design.proxy.spring..*.sleep(..))")
	public void point() {

	}

}