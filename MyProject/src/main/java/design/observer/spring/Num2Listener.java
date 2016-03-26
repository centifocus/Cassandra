package design.observer.spring;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @see supportsEventType：用于指定支持的事件类型；
 * @see supportsSourceType：支持的目标类型；
 * @see getOrder：顺序，越小优先级越高
 *
 */
@Component
public class Num2Listener implements SmartApplicationListener {

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
		return 2;
	}

}
