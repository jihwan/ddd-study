package ddd;

import java.util.Set;

public interface OrderRepository {

	Set<Order> findByCustomer(Customer customer);

	void save(Order with);

	Order find(String orderName);

	void delete(Order order);
}
