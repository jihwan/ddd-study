package ddd;

import java.util.HashSet;
import java.util.Set;

import common.EntryPoint;

public class Order extends EntryPoint {
	
	private Set<OrderItem> items = new HashSet<>();
	private Customer customer;

	Order(String identity, Customer customer) {
		super(identity);
		this.customer = customer;
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
		
		for (OrderItem item : items) {
			if (item.isProductEqual(orderItem)) {
				item.merge(orderItem);
				return this;
			}
		}
		
		this.items.add(orderItem);
		return this;
	}

	private boolean isExceedLimit(OrderItem orderItem) {
		return customer.isExceedLimitPrice(getPrice().add(orderItem.getPrice()));
	}

	public Money getPrice() {
		
		Money result = Money.ZERO;
		
		for (OrderItem item : items) {
			result = result.add(item.getPrice());
		}
		
		return result;
	}

	public int getOrderItemSize() {
		return items.size();
	}

	public boolean isOrderedBy(Customer another) {
		return this.customer == another;
	}

}
