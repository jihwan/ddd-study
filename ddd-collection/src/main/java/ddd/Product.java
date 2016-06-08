package ddd;

import common.EntryPoint;

public class Product extends EntryPoint {

	private Money price;
	
	public Product(String name, long price) {
		super(name);
		this.price = new Money(price);
	}

	public Money getPrice() {
		return price;
	}
}
