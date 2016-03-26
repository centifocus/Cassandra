package spring.annotation.conditional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

//Indicates that a class declares one or more @Bean methods and may be processed by the Spring container to generate bean at runtime
@Configuration
public class DemoConfig {

	@Bean
	@Conditional(CustomCondition1.class)
	public MyBean commandService() {
		return new MyBean();
	}

}