package impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import ddd.Customer;
import ddd.CustomerRepository;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {
	
	@PersistenceContext
	EntityManager em;

	@Override
	public void save(Customer customer) {
		em.persist(customer);
	}

}
