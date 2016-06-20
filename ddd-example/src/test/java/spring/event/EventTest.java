package spring.event;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={EventTest.AppConfig.class})
public class EventTest {
	
	@Autowired
	GenericApplicationContext context;
	
//	@Test
//	public void testEvent() throws Exception {
//		
//		
//		MyEvent myEvent = new MyEvent(this);
//		context.publishEvent(myEvent);
//		
//		for (String beanName : context.getBeanDefinitionNames()) {
//			System.err.println(beanName);
//		}
//	}
	
	@Autowired
	ApplicationEventPublisher publisher;
	
	@Test
	public void testPublishEvent() throws Exception {
		for (String beanName : context.getBeanDefinitionNames()) {
			System.err.println(beanName);
		}
		
		MyEvent myEvent = new MyEvent(this);
		publisher.publishEvent(myEvent);
		

	}

	@Configuration
	@ComponentScan
	static class AppConfig {
	}
}
