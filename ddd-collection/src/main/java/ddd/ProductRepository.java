package ddd;

import common.Registrar;

public class ProductRepository {

	public void save(Product product) {
		
		Registrar.add(product.getClass(), product);
	}

	public Product find(String productName) {
		return (Product) Registrar.get(Product.class, productName);
	}

}
