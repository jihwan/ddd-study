package dddtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import config.AppConfig;
import ddd.Customer;
import ddd.Money;
import ddd.Order;
import ddd.OrderLimitExceededException;
import ddd.OrderRepository;
import ddd.Product;
import ddd.ProductRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AppConfig.class})
@Transactional
public class OrderTest {
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	Customer customer;
	
	@PersistenceContext
	EntityManager em;
	
	@Before
	public void setup() {
		
		productRepository.save(new Product("prod1", 1000));
		productRepository.save(new Product("prod2", 5000));
		
		customer = new Customer("CUST-01", "zhwan", "seoul", 200000);
	}

	@Test
	public void testOrderPrice() {
		Order order = 
				customer.newOrder("CUST-01-ORDER-01")
				.with("prod1", 10)
				.with("prod2", 20);
		
		orderRepository.save(order);
		assertEquals(new Money(110000), order.getPrice());
	}
	
	@Test(expected=OrderLimitExceededException.class)
	public void testOrderLimitExceed() throws Exception {
		
		customer.newOrder("CUST-01-ORDER-01")
		.with("prod1", 100)
		.with("prod2", 200);
	}
	
	@Test
	public void testOrderWithEqualProductsPrice() throws Exception {
		Order order = 
				customer.newOrder("CUST-01-ORDER-01")
				.with("prod1", 5)
				.with("prod2", 20)
				.with("prod1", 5);
		
		orderRepository.save(order);
		assertEquals(new Money(110000), order.getPrice());
	}
	
	@Test
	public void testOrderItems() throws Exception {
		Order order = 
				customer.newOrder("CUST-01-ORDER-01")
				.with("prod1", 5)
				.with("prod2", 20)
				.with("prod1", 5);
		
		orderRepository.save(order);
		assertEquals(2, order.getOrderItemSize());
		assertEquals(new Money(110000), order.getPrice());
	}
	
	// why error???
	@Ignore
	@Test
	public void testOrderIdentical() throws Exception {
		
		em.flush();
		
		Order order = 
				customer.newOrder("CUST-01-ORDER-01")
				.with("prod1", 10)
				.with("prod2", 20);
		orderRepository.save(order);
		
		
		
		Order another = orderRepository.find("CUST-01-ORDER-01");
		assertEquals(order, another);
		assertSame(order, another);
	}
	
	@Ignore
	@Test//(expected=NoResultException.class)
	public void testDeleteOrder() throws Exception {
		
		Order order = 
				customer.newOrder("CUST-01-ORDER-01")
				.with("prod1", 10)
				.with("prod2", 20);
		orderRepository.save(order);
		
		orderRepository.delete(order);
		Order another = orderRepository.find("CUST-01-ORDER-01");
		assertNull(another);
		assertNotNull(order);
	}
}
