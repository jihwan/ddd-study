package ddd;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

	Set<Order> findByCustomer(Customer customer);

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
