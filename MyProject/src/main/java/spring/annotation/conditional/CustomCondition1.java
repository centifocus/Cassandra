package spring.annotation.conditional;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class CustomCondition1 implements Condition {

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {

		System.out.println(System.getProperty("os.name"));
		if (System.getProperty("os.name").equals("Windows 7")) {
			return true;
		} else {
			return false;
		}

	}

}