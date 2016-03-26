package design.observer.spring;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
/**
 * 异步调用onApplicationEvent
 */
public class AsyncListener implements ApplicationListener<MyEvent> {

	@Async
	@Override
	public void onApplicationEvent(final MyEvent event) {
		System.out.println(this.getClass() + " ：" + event.getSource());
	}
}
