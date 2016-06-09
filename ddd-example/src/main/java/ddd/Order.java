package ddd;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name="ORDERS")// @Table(name="ORDERS")
public class Order {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="order_name")
	private String orderName;
	
	@ManyToOne(optional=true)
//	@JoinColumn(name="customer_id", )
	private Customer customer;
	
	@OneToMany(cascade={CascadeType.ALL}, orphanRemoval=true)
	@JoinColumn(name="order_name")//, referencedColumnName="order_name", nullable=false)
	private Set<OrderItem> orderItems = new HashSet<>();

	Order() {}
	
	Order(String orderName, Customer customer) {
		this.orderName = orderName;
		this.customer = customer;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getOrderName() {
		return orderName;
	}

	public static Order order(String orderId, Customer customer) {
		return new Order(orderId, customer);
	}
	
	public Order with(String productName, int quantity) throws OrderLimitExceededException {
		
		return with(new OrderItem(productName, quantity));
	}
	
	private Order with(OrderItem orderItem) throws OrderLimitExceededException {
		
		if (isExceedLimit(orderItem)) {
			throw new OrderLimitExceededException();
		}
		
		for (OrderItem item : orderItems) {
			if (item.isProductEqual(orderItem)) {
				item.merge(orderItem);
				return this;
			}
		}
		
		this.orderItems.add(orderItem);
		return this;
	}

	private boolean isExceedLimit(OrderItem orderItem) {
		return customer.isExceedLimitPrice(getPrice().add(orderItem.getPrice()));
	}

	public Money getPrice() {
		
		Money result = Money.ZERO;
		
		for (OrderItem item : orderItems) {
			result = result.add(item.getPrice());
		}
		
		return result;
	}

	public int getOrderItemSize() {
		return orderItems.size();
	}

	public boolean isOrderedBy(Customer another) {
		return this.customer == another;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getOrderName() == null) ? 0 : getOrderName().hashCode());
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
		Order other = (Order) obj;
		if (getOrderName() == null) {
			if (other.getOrderName() != null)
				return false;
		} else if (!getOrderName().equals(other.getOrderName()))
			return false;
		return true;
	}
}
