package spring.annotation.conditional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Conditional;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
// 指定条件判断的类CustomCondition
@Conditional(CustomCondition.class)
/**
 * The @Conditional annotation may be used in any of the following ways: 
 as a type-level annotation on any class directly or indirectly annotated with @Component, including @Configuration classes 
 as a meta-annotation, for the purpose of composing custom stereotype annotations 
 as a method-level annotation on any @Bean method 
 */
public @interface Local {
	public final String id = "Local";
}