package by.epam.coffeemashine.model.data.entities;

import by.epam.coffeemashine.model.data.entities.enums.IngredientTypeEnum;

/** 
 * Class {@code Ingredient} is an extension of {@code AbstractProduct} 
 * and represents a ingredient which user can add in a coffee.
 * 
 * @author Andrei Padhaiski
 * 
*/
public class Ingredient extends AbstractProduct {
	
	private static final long serialVersionUID = 1L;
	
	private IngredientTypeEnum type;
	
	public IngredientTypeEnum getType() {
		return type;
	}
	public void setType(IngredientTypeEnum type) {
		this.type = type;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
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
		Ingredient other = (Ingredient) obj;
		if (type != other.type)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Ingredient [").append(super.toString())
				.append(", type=").append(type).append("]");
		return builder.toString();
	}
}
