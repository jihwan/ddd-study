package impl;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import ddd.Customer;
import ddd.Order;
import ddd.OrderRepository;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

	@PersistenceContext
	EntityManager em;
	
	@Override
	public Set<Order> findByCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Order with) {
		// TODO Auto-generated method stub

	}

}
