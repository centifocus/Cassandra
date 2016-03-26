package spring.annotation.conditional;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class CustomCondition implements Condition {

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		boolean isLocalAnnotated = metadata.isAnnotated(Local.class.getName());
		boolean isRemoteAnnotated = metadata.isAnnotated(Remote.class.getName());

		// 如果bean没有注解@Local或@Remote，肯定创建。
		if (!isLocalAnnotated && !isRemoteAnnotated) {
			return true;
		} else {
			String[] profiles = context.getEnvironment().getActiveProfiles();
			if (profiles.length == 0) {
				return false;
			}

			switch (profiles[0]) {
			case Local.id:
				return isLocalAnnotated;
			case Remote.id:
				return isRemoteAnnotated;
			}
		}

		return false;
	}

}