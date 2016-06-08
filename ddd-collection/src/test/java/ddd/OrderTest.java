package ddd;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import common.Registrar;

public class OrderTest {
	
	Customer customer;
	OrderRepository orderRepository;
	ProductRepository productRepository;
	
	@Before
	public void setup() {
		Registrar.init();
		orderRepository = new OrderRepository();
		productRepository = new ProductRepository();
		
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
	
	@Test
	public void testOrderIdentical() throws Exception {
		
		Order order = 
				customer.newOrder("CUST-01-ORDER-01")
				.with("prod1", 10)
				.with("prod2", 20);
		orderRepository.save(order);
		
		Order another = orderRepository.find("CUST-01-ORDER-01");
		assertEquals(order, another);
		assertSame(order, another);
	}
	
	@Test
	public void testDeleteOrder() throws Exception {
		
		Order order = 
				customer.newOrder("CUST-01-ORDER-01")
				.with("prod1", 10)
				.with("prod2", 20);
		orderRepository.save(order);
		
		orderRepository.delete("CUST-01-ORDER-01");
		Order another = orderRepository.find("CUST-01-ORDER-01");
		assertNull(another);
		assertNotNull(order);
	}
}
