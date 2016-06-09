package impl;

import java.util.HashSet;
import java.util.List;
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
		
		List<Order> resultList = em.createQuery("select o from Order o where o.customer.name = :name", Order.class)
		.setParameter("name", customer.getName())
		.getResultList();
		return new HashSet<Order>(resultList);
	}

	@Override
	public void save(Order order) {
		em.persist(order);
	}

	@Override
	public Order find(String orderName) {
		return em.createQuery("select o from Order o where o.orderName = :orderName", Order.class)
		.setParameter("orderName", orderName)
		.getSingleResult();
	}

	@Override
	public void delete(Order order) {
		em.remove(order);
	}

}
