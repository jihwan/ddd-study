package ddd;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.AppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
public class ProductRepositoryTest {

	@Autowired
	GenericApplicationContext context;

	@Ignore
	@Test
	public void testContext() throws Exception {

		for (String beanName : context.getBeanDefinitionNames()) {
			System.err.println(beanName);
		}
	}
	
	@Autowired
	ProductRepository productRepository;
	
	@Before
	public void setup() {
	}

	@Test
	public void testSave() {

		Product saveProduct = new Product("상품1", 1000);
		productRepository.save(saveProduct);

		assertSame(saveProduct, productRepository.find("상품1"));
	}

	@Test
	public void testFind() {
		
		Product saveProduct = new Product("상품1", 1000);
		productRepository.save(saveProduct);
		
		Product find = productRepository.find("상품1");
		assertSame(saveProduct, find);
	}

}
