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
		return em.createQuery("select p from Product p where p.name = :name", Product.class)
		.setParameter("name", name)
		.getSingleResult();
	}

	@Override
	public void save(Product product) {
		em.persist(product);
	}

}
