package dddtest;

import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import config.AppConfig;
import ddd.Product;
import ddd.ProductRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
@Transactional
public class ProductRepositoryTest {

	@Autowired
	GenericApplicationContext context;

	@Autowired
	ProductRepository productRepository;
	
	@Test
	public void testSave() {

		Product saveProduct = new Product("상품1", 1000);
		productRepository.save(saveProduct);

		assertSame(saveProduct, productRepository.findByName("상품1"));
	}

	@Test
	public void testFind() {
		
		Product saveProduct = new Product("상품1", 1000);
		productRepository.save(saveProduct);
		
		Product find = productRepository.findByName("상품1");
		assertSame(saveProduct, find);
	}

}
