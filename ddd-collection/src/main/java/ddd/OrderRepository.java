package ddd;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import common.Registrar;

public class OrderRepository {

	public void save(Order order) {

		Registrar.add(order.getClass(), order);
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
				((Collection<Order>) Registrar.getAll(Order.class))
				);
	}

	public Order find(String orderId) {
		return (Order) Registrar.get(Order.class, orderId);
	}

	public void delete(String orderId) {
		Registrar.delete(Order.class, orderId);
	}


}
