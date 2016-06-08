package ddd;

public class OrderItem {

	Product product;
	int quantity;
	
	private ProductRepository productRepository = new ProductRepository();
	
	public OrderItem(String productName, int quantity) {
		this.product = productRepository.find(productName);
		this.quantity = quantity;
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
