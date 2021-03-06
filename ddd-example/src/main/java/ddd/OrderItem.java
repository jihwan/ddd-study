package ddd;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Entity @Configurable(preConstruction=true)
@org.hibernate.envers.Audited
public class OrderItem {
	
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(name="product_id", nullable=false)
	private Product product;
	
	@ManyToOne
	@JoinColumn(name="order_id", insertable=false, updatable=false)
	private Order order;
	
	int quantity;
	
	@Autowired
	transient ProductRepository productRepository;
	
	OrderItem() {}
	
	OrderItem(Product product, int quantity) {
		this.product = product;
		this.quantity = quantity;
	}
	
	OrderItem(String productName, int quantity) {
		this.product = productRepository.findByName(productName);
		this.quantity = quantity;
	}
	
	
	public Long getId() {
		return id;
	}
	
	public Product getProduct() {
		return product;
	}
	
	public int getQuantity() {
		return quantity;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getProduct() == null) ? 0 : getProduct().hashCode());
		result = prime * result + getQuantity();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderItem other = (OrderItem) obj;
		if (getProduct() == null) {
			if (other.getProduct() != null)
				return false;
		} else if (!getProduct().equals(other.getProduct()))
			return false;
		if (getQuantity() != other.getQuantity())
			return false;
		return true;
	}
}
