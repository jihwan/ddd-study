package ddd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable(preConstruction=true)
public class OrderItem {

	Product product;
	int quantity;
	
	@Autowired
	private ProductRepository productRepository;
	
	public OrderItem(String productName, int quantity) {
		this.product = productRepository.find(productName);
		this.quantity = quantity;
	}
	
	public void setProductRepository(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	public Money getPrice() {
		return product.getPrice().multiply(quantity);
	}

	public boolean isProductEqual(OrderItem orderItem) {
		return this.product == orderItem.product;
	}

	public void merge(OrderItem orderItem) {
		this.quantity += orderItem.quantity;
	}
}
