package ddd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import common.Registrar;

@Repository
public class CustomerRepository {

	@Autowired
	private Registrar registrar;
	
	public void save(Customer customer) {
		registrar.add(Customer.class, customer);
	}

	public Customer find(String identity) {
		return (Customer) registrar.get(Customer.class, identity);
	}
}