package ddd;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Customer {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String customerNumber;
	
	private String name;
	
	@SuppressWarnings("unused")
	private String address;
	
	@Embedded
	@AttributeOverride(name="amount", column=@Column(name="mileage"))
	private Money mileage;
	
	@Embedded
	@AttributeOverride(name="amount", column=@Column(name="limitPrice"))
	private Money limitPrice;
	
	Customer() {}

	public Customer(String customerNumber, String name, String address, long limitPrice) {
		this.customerNumber = customerNumber;
		this.name = name;
		this.address = address;
		this.limitPrice = new Money(limitPrice);
	}
	
	public String getName() {
		return name;
	}
	
	public String getCustomerNumber() {
		return customerNumber;
	}

	public Order newOrder(String orderId) {
		return Order.order(orderId, this);
	}

	public boolean isExceedLimitPrice(Money money) {
		return money.isGreaterThan(limitPrice);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getCustomerNumber() == null) ? 0 : getCustomerNumber().hashCode());
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
		Customer other = (Customer) obj;
		if (getCustomerNumber() == null) {
			if (other.getCustomerNumber() != null)
				return false;
		} else if (!getCustomerNumber().equals(other.getCustomerNumber()))
			return false;
		return true;
	}

}
