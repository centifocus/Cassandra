package design.proxy.spring.aop;

import org.aspectj.lang.JoinPoint;

/**
 * Object[] getArgs：返回目标方法的参数
 * 
 * Signature getSignature：返回目标方法的签名
 * 
 * Object getTarget：返回被织入增强处理的目标对象
 * 
 * Object getThis：返回AOP框架为目标对象生成的代理对象
 * 
 * 注意：当使用@Around处理时，我们需要将第一个参数定义为ProceedingJoinPoint类型，该类是JoinPoint的子类。
 */

public class SleepHelper {

	// 任何通知方法的第一个参数都可以是JoinPoint
	public void beforeSleep(JoinPoint jp) {
		System.out.println("睡觉前要脱衣服!  " + jp.getSignature().getName());
	}

	public void afterSleep(JoinPoint jp) {
		System.out.println("睡醒了要穿衣服!  " + jp.getArgs()[0]);
	}

	public void handleException(JoinPoint jp, Exception ex) {
		System.out.println(ex.toString());
	}

}