package ltw;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={LTWTest.AppConfig.class})
public class LTWTest {
	
	@Autowired
	GenericApplicationContext context;

	@Test
	public void testLtw() throws Exception {
		
//		for (String bean : context.getBeanDefinitionNames()) {
//			System.err.println(bean);
//		}
		
		new Domain();
	}
	
	@Configuration
	@ComponentScan({"ltw", "abcimpl"})
	@EnableSpringConfigured
	@EnableLoadTimeWeaving
	@EnableTransactionManagement
	static class AppConfig {
	}
}
