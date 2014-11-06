package by.epam.coffeemashine.model.data.dao.interfaces;

import java.util.List;

import by.epam.coffeemashine.model.data.dao.exception.DAOException;
import by.epam.coffeemashine.model.data.entities.Order;

public interface IOrderDAO extends IDAO<Integer, Order> {
	
	List<Order> findOrdersByUserId(Integer id) throws DAOException;

	boolean executeTransaction(Order order) throws DAOException;
}
