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
import org.springframework.transaction.annotation.Transactional;

import config.AppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AppConfig.class})
@Transactional
public class OrderRepositoryTest {
	
	@Autowired
	GenericApplicationContext context;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	CustomerRepository customorRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	Customer customer;
	Product product1;
	Product product2;
	
	@Before
	public void setup() throws Exception {
		
		product1 = new Product("prod1", 1000);
		product2 = new Product("prod2", 5000);
		
		productRepository.save(product1);
		productRepository.save(product2);
		
		customer = new Customer("CUST-01", "zhwan", "seoul", 200000);
		customorRepository.save(customer);
	}
	
	@Ignore
	@Test
	public void testOrderCount2() {
		orderRepository.save(customer.newOrder("CUST-01-ORDER-01")
				.with(product1, 5)
				.with(product2, 20)
				.with(product1, 5));
		orderRepository.save(customer.newOrder("CUST-01-ORDER-02")
				.with(product1, 20)
				.with(product2, 5));
		
		assertEquals(2, orderRepository.findByCustomer(customer).size());
	}
	
	@Ignore
	@Test
	public void testOrderFind() {
		
		Order order = customer.newOrder("CUST-01-ORDER-01")
		.with(product1, 5)
		.with(product2, 20)
		.with(product1, 5);
		orderRepository.save(order);
		
		orderRepository.save(customer.newOrder("CUST-01-ORDER-02")
				.with(product1, 20)
				.with(product2, 5));
		
		Order find = orderRepository.find("CUST-01-ORDER-01");
		
		assertSame(order, find);
	}
	
	@Ignore
	@Test
	public void testOrderDelete() {
		
		Order order = customer.newOrder("CUST-01-ORDER-01")
				.with(product1, 5)
				.with(product2, 20)
				.with(product1, 5);
		orderRepository.save(order);
		
		orderRepository.save(customer.newOrder("CUST-01-ORDER-02")
				.with(product1, 20)
				.with(product2, 5));
		
		Order find = orderRepository.find("CUST-01-ORDER-01");
		assertNotNull(find);
		
		orderRepository.delete(order);
	}
	
	
	
	// OrderItem LTW do not work!!!
	@Test
	public void testOrderCount() {
		orderRepository.save(customer.newOrder("CUST-01-ORDER-01")
                .with("prod1", 5)
                .with("prod2", 20)
                .with("prod1", 5));
		orderRepository.save(customer.newOrder("CUST-01-ORDER-02")
		                .with("prod1", 20)
		                .with("prod2", 5));
		
		assertEquals(2, orderRepository.findByCustomer(customer).size());
	}

}
