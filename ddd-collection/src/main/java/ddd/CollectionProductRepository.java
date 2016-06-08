package ddd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import common.Registrar;

@Repository
public class CollectionProductRepository implements ProductRepository {
	
	@Autowired
	private Registrar registrar;
	
	@Override
	public void save(Product product) {
		
		registrar.add(product.getClass(), product);
	}

	@Override
	public Product find(String productName) {
		return (Product) registrar.get(Product.class, productName);
	}

}
