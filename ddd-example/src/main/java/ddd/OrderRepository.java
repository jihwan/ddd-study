package ddd;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import common.Registrar;

@Repository
public class OrderRepository {

	@Autowired
	private Registrar registrar;
	
	public void save(Order order) {

		registrar.add(order.getClass(), order);
	}

	public Set<Order> findByCustomer(Customer customer) {
		
		Set<Order> results = new HashSet<>();
		
		for (Order order : findAll()) {
			if (order.isOrderedBy(customer)) {
				results.add(order);
			}
		}
		
		return results;
	}

	@SuppressWarnings("unchecked")
	public Set<Order> findAll() {
		return new HashSet<>(
				((Collection<Order>) registrar.getAll(Order.class))
				);
	}

	public Order find(String orderId) {
		return (Order) registrar.get(Order.class, orderId);
	}

	public void delete(String orderId) {
		registrar.delete(Order.class, orderId);
	}


}
