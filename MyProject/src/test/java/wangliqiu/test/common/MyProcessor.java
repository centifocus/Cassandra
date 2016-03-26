package wangliqiu.test.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class MyProcessor {

	public void process(Object instance) throws Exception {
		for (Method method : instance.getClass().getDeclaredMethods()) {
			// RenderMethod rm = (RenderMethod) method.getAnnotation(RenderMethod.class);

			System.out.println("type   " + method.getParameterTypes());
			Object[] parameters = new Object[method.getParameters().length];
			int i = 0;
			for (Annotation[] annotations : method.getParameterAnnotations()) {
				for (Annotation annotation : annotations) {
					System.out.println(((ParmInsert) annotation).name());
					parameters[i++] = ((ParmInsert) annotation).name();
				}
			}

			method.invoke(instance, parameters);

		}
	}

}
