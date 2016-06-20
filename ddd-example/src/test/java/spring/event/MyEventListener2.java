package spring.event;

import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class MyEventListener2 implements ApplicationListener<MyEvent> {

//	@EventListener
//	public void handleMyEvent(MyEvent event) {
	
	@Override
	public void onApplicationEvent(MyEvent event) {

		try {
			Thread.sleep(700);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.err.println("2 : " + System.currentTimeMillis());
//		System.out.println("2 event source : " + event.getSource());
//		System.out.println("2 event : " + event);
	}

}
