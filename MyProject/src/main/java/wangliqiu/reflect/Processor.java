package wangliqiu.reflect;

import java.lang.reflect.Method;

public class Processor {

	public void process(Object instance) {
		for (Method method : instance.getClass().getDeclaredMethods()) {
			RenderMethod rm = (RenderMethod) method.getAnnotation(RenderMethod.class);

			if (rm != null) {
				Object[] parameters = rm.parameters().length > 0 ? buildParameters(rm.parameters()) : null;

				try {
					method.invoke(instance, parameters);
				} catch (Exception e) {
					e.printStackTrace();
				}

				break;
			}
		}
	}

	// 根据注解数组创建参数对象列表，供 invoke 使用
	private Object[] buildParameters(RenderParameter[] parameters) {
		Object[] objs = new Object[parameters.length];
		int i = 0;

		for (RenderParameter parameter : parameters) {
			RenderParameter.ScopeType scope = parameter.scope();

			if (scope == RenderParameter.ScopeType.NORMAL) {
				String value = parameter.name();
				// dosomething on value
				objs[i++] = value;

			} else if (scope == RenderParameter.ScopeType.SESSION) {
				String value = parameter.name();
				// dosomething on value
				if (parameter.type() == RenderParameter.ParameterType.BOOL) {
					objs[i++] = new Boolean(false);
				} else {
					objs[i++] = "wangliqiu";
				}

			} else if (scope == RenderParameter.ScopeType.COOKIE) {
				String value = parameter.name();
				// dosomething on value
				objs[i++] = value;
			}

		}

		return objs;
	}

}
