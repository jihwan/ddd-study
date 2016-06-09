package impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import ddd.Product;
import ddd.ProductRepository;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

	@PersistenceContext
	EntityManager em;
	
	@Override
	public Product findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Product product) {
		em.persist(product);
	}

}
