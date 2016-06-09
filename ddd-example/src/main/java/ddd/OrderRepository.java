package ddd;

import java.util.Set;

public interface OrderRepository {

	Set<Order> findByCustomer(Customer customer);

	void save(Order with);

//	public Set<Order> findByCustomer(Customer customer) {
//		
//		Set<Order> results = new HashSet<>();
//		
//		for (Order order : findAll()) {
//			if (order.isOrderedBy(customer)) {
//				results.add(order);
//			}
//		}
//		
//		return results;
//	}
//
//	public Order find(String orderId) {
//		return (Order) registrar.get(Order.class, orderId);
//	}
//
//	public void delete(String orderId) {
//		registrar.delete(Order.class, orderId);
//	}
}
