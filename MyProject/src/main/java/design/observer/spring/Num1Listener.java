package design.observer.spring;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class Num1Listener implements SmartApplicationListener {

	@Override
	public boolean supportsEventType(final Class<? extends ApplicationEvent> eventType) {
		return eventType == MyEvent.class;
	}

	@Override
	public boolean supportsSourceType(final Class<?> sourceType) {
		return sourceType == String.class;
	}

	@Override
	public void onApplicationEvent(final ApplicationEvent event) {
		System.out.println(this.getClass() + " ：" + event.getSource());
	}

	@Override
	public int getOrder() {
		return 1;
	}
}