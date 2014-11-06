package by.epam.coffeemashine.model.data.entities;

import java.math.BigDecimal;

/** 
 * This class provides a base fields, which has every product.
 * 
 * @author Andrei Padhaiski
 * 
*/
public abstract class AbstractProduct extends AbstractEntity {
	
	private static final long serialVersionUID = 416632817182626137L;
	
	private BigDecimal price;
	private int quantity;

	public BigDecimal getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + quantity;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractProduct other = (AbstractProduct) obj;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (quantity != other.quantity)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString())
				.append(", price=").append(price)
				.append(", quantity=").append(quantity);
		return builder.toString();
	}

}
