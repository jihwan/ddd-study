package ddd;

import javax.persistence.Embeddable;

@Embeddable
public class Money {

	private long amount;
	
	public transient static Money ZERO = new Money(0);
	
	Money(){}
	
	public Money(long amount) {
		this.amount = amount;
	}
	
	public long getAmount() {
		return amount;
	}

	public Money add(Money money) {
		
		return new Money(getAmount() + money.getAmount());
	}

	public boolean isGreaterThan(Money limitPrice) {
		
		if (this.getAmount() > limitPrice.getAmount()) {
			return true;
		}
		else {
			return false;
		}
	}

	public Money multiply(int quantity) {
		return new Money(getAmount() * quantity);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (getAmount() ^ (getAmount() >>> 32));
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
		if (getAmount() != other.getAmount())
			return false;
		return true;
	}
}
