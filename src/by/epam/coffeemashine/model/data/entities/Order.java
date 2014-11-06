package by.epam.coffeemashine.model.data.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/** 
 * Class {@code Order} represents the order, 
 * which can user make in a coffee-machine.
 * 
 * @author Andrei Padhaiski
 * 
*/
public class Order extends AbstractEntity {
	private static final long serialVersionUID = -7678462263257594342L;
	
	private User user;
	private Coffee coffee;
	private Set<Ingredient> ingredients;
	private BigDecimal amount;
	private LocalDate date;

	public User getUser() {
		return user;
	}

	public Coffee getCoffee() {
		return coffee;
	}

	public Set<Ingredient> getIngredients() {
		return ingredients;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setCoffee(Coffee coffee) {
		this.coffee = coffee;
	}

	public void setIngredients(Set<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((coffee == null) ? 0 : coffee.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((ingredients == null) ? 0 : ingredients.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		Order other = (Order) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (coffee == null) {
			if (other.coffee != null)
				return false;
		} else if (!coffee.equals(other.coffee))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (ingredients == null) {
			if (other.ingredients != null)
				return false;
		} else if (!ingredients.equals(other.ingredients))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Order [").append(super.toString())
				.append(", user=").append(user)
				.append(", coffee=").append(coffee)
				.append(", ingredients=").append(ingredients)
				.append(", amount=").append(amount)
				.append(", date=").append(date).append("]");
		return builder.toString();
	}
}
