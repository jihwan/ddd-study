package ddd;

public interface ProductRepository {

	Product findByName(String name);

	void save(Product product);
	
}