package by.epam.coffeemashine.model.data.entities;

import by.epam.coffeemashine.model.data.entities.enums.CoffeeSortEnum;
import by.epam.coffeemashine.model.data.entities.enums.CoffeeTypeEnum;

/** 
 * Class {@code Coffee} is an extension of {@code AbstractProduct} 
 * and represents a Coffee beverage.
 * 
 * @author Andrei Padhaiski
 * 
*/
public class Coffee extends AbstractProduct {

	private static final long serialVersionUID = -5018017927907015917L;

	private CoffeeTypeEnum type;
	private CoffeeSortEnum sort;

	public CoffeeTypeEnum getType() {
		return type;
	}

	public CoffeeSortEnum getSort() {
		return sort;
	}

	public void setType(CoffeeTypeEnum type) {
		this.type = type;
	}

	public void setSort(CoffeeSortEnum sort) {
		this.sort = sort;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((sort == null) ? 0 : sort.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Coffee other = (Coffee) obj;
		if (sort != other.sort)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Coffee [").append(super.toString())
				.append(", type=").append(type)
				.append(", sort=").append(sort).append("]");
		return builder.toString();
	}
}
