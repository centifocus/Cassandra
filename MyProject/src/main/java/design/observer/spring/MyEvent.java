package design.observer.spring;

import org.springframework.context.ApplicationEvent;

public class MyEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	public MyEvent(final String content) {
		super(content);

	}

}