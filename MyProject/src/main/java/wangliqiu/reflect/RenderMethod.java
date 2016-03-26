package wangliqiu.reflect;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface RenderMethod {

	public enum MethodType {
		INQUIRE, UPDATE
	}

	public MethodType methodType() default MethodType.UPDATE;

	public RenderParameter[] parameters(); // 参数列表

}
