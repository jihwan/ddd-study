package ddd;


public class Money {

	private long price;
	
	public static Money ZERO = new Money(0);
	
	public Money(long price) {
		this.price = price;
	}
	
	public long getPrice() {
		return price;
	}

	public Money add(Money money) {
		
		return new Money(getPrice() + money.getPrice());
	}

	public boolean isGreaterThan(Money limitPrice) {
		
		if (this.getPrice() > limitPrice.getPrice()) {
			return true;
		}
		else {
			return false;
		}
	}

	public Money multiply(int quantity) {
		return new Money(price * quantity);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (price ^ (price >>> 32));
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
		Money other = (Money) obj;
		if (price != other.price)
			return false;
		return true;
	}
}
