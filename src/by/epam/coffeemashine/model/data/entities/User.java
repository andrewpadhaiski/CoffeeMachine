package by.epam.coffeemashine.model.data.entities;

import java.math.BigDecimal;

import by.epam.coffeemashine.model.data.entities.enums.UserRoleEnum;

/** 
 * Class {@code User} represents the user of coffee-machine, 
 * 
 * @author Andrei Padhaiski
 * 
*/
public class User extends AbstractEntity {

	private static final long serialVersionUID = 5589005990644503183L;

	private String name;
	private String login;
	private String password;
	private UserRoleEnum role;
	private BigDecimal balance;

	public String getName() {
		return name;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public UserRoleEnum getRole() {
		return role;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRole(UserRoleEnum role) {
		this.role = role;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
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
		User other = (User) obj;
		if (balance == null) {
			if (other.balance != null)
				return false;
		} else if (!balance.equals(other.balance))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (role != other.role)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [").append(super.toString())
				.append("name=").append(name)
				.append(", login=").append(login)
				.append(", password=").append(password)
				.append(", role=").append(role)
				.append(", balance=").append(balance).append("]");
		return builder.toString();
	}
	
}
