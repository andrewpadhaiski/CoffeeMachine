package by.epam.coffeemashine.model.services.interfaces;

import java.util.List;
import java.util.Map;

import by.epam.coffeemashine.model.data.entities.Coffee;
import by.epam.coffeemashine.model.data.entities.Order;
import by.epam.coffeemashine.model.data.entities.User;
import by.epam.coffeemashine.model.services.exception.ServiceException;

public interface IOrderService {
	Order makeAnOrder(User user, Coffee coffee, Map<Integer, Integer> additions)
			throws ServiceException;
	
	List<Order> getOrdersByUser(User user) throws ServiceException;

	List<Order> getOrders() throws ServiceException;

	boolean transact(Order order) throws ServiceException;
}
