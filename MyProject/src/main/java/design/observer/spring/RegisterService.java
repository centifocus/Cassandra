package design.observer.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

// This annotation serves as a specialization of @Component,
@Service
/**
 * 封装一层service  
 */
public class RegisterService {

	@Autowired
	private ApplicationContext applicationContext;

	public void register(String event) {
		applicationContext.publishEvent(new MyEvent(event));
	}

}
