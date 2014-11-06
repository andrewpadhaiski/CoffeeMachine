package by.epam.coffeemashine.model.data.entities;

import java.io.Serializable;

/** 
 * Class {@code AbstractEntity} is root of entities hierarchy.
 * Every entity has {@code AbstractEntity} as a superclass.
 * 
 * @author Andrei Padhaiski
 * 
*/
public abstract class AbstractEntity implements Serializable  {

	private static final long serialVersionUID = 7497600984145335462L;
	
	private int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		AbstractEntity other = (AbstractEntity) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("id=").append(id);
		return builder.toString();
	}
	
}
