package ddd;

import common.EntryPoint;

public class Customer extends EntryPoint {
	
	@SuppressWarnings("unused")
	private String customerNumber;
	@SuppressWarnings("unused")
	private String name;
	@SuppressWarnings("unused")
	private String address;
	@SuppressWarnings("unused")
	private Money mileage;
	
	private Money limitPrice;

	public Customer(String customerNumber, String name, String address, long limitPrice) {
		super(customerNumber);
		this.customerNumber = customerNumber;
		this.name = name;
		this.address = address;
		this.limitPrice = new Money(limitPrice);
	}

	public Order newOrder(String orderId) {
		return Order.order(orderId, this);
	}

	public boolean isExceedLimitPrice(Money money) {
		return money.isGreaterThan(limitPrice);
	}
}
