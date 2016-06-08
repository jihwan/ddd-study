package ddd;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import common.Registrar;

public class OrderRepositoryTest {
	
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
