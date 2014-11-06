package by.epam.coffeemashine.model.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import by.epam.coffeemashine.model.data.dao.IngredientDAO;
import by.epam.coffeemashine.model.data.dao.OrderDAO;
import by.epam.coffeemashine.model.data.dao.exception.DAOException;
import by.epam.coffeemashine.model.data.dao.interfaces.IIngredientDAO;
import by.epam.coffeemashine.model.data.dao.interfaces.IOrderDAO;
import by.epam.coffeemashine.model.data.entities.Coffee;
import by.epam.coffeemashine.model.data.entities.Ingredient;
import by.epam.coffeemashine.model.data.entities.Order;
import by.epam.coffeemashine.model.data.entities.User;
import by.epam.coffeemashine.model.services.exception.ServiceException;
import by.epam.coffeemashine.model.services.interfaces.IOrderService;

public class OrderService implements IOrderService {

	private static final IOrderDAO ORDERS = OrderDAO.getInstance();
	private static final IIngredientDAO INGREDIENTS = IngredientDAO
			.getInstance();

	private static final IOrderService INSTANCE = new OrderService();

	private OrderService() {
	}

	public static final IOrderService getInstance() {
		return INSTANCE;
	}

	@Override
	public Order makeAnOrder(User user, Coffee coffee,
			Map<Integer, Integer> additions) throws ServiceException {
		Order order = new Order();
		order.setUser(user);
		order.setCoffee(coffee);
		order.setDate(LocalDate.now());
		Set<Ingredient> ingredients = new HashSet<>();

		BigDecimal amount = coffee.getPrice();

		Set<Integer> ingredientIds = additions.keySet();
		for (Integer id : ingredientIds) {
			Ingredient ingredient;
			try {
				ingredient = INGREDIENTS.findEntityById(id);
			} catch (DAOException e) {
				throw new ServiceException(e);
			}
			int orderedQuantity = additions.get(id);
			ingredient.setQuantity(orderedQuantity);
			BigDecimal ingredientCost = ingredient.getPrice().multiply(
					BigDecimal.valueOf(orderedQuantity));
			amount = amount.add(ingredientCost);
			ingredients.add(ingredient);
		}
		order.setIngredients(ingredients);
		order.setAmount(amount);
		return order;
	}

	@Override
	public List<Order> getOrdersByUser(User user) throws ServiceException {
		List<Order> orders = null;
		int userId = user.getId();
		try {
			orders = ORDERS.findOrdersByUserId(userId);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return orders;
	}

	@Override
	public List<Order> getOrders() throws ServiceException {
		List<Order> orders = null;
		try {
			orders = ORDERS.findAll();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return orders;
	}

	@Override
	public boolean transact(Order order) throws ServiceException {
		boolean flag = false;
		try {
			flag = ORDERS.executeTransaction(order);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return flag;
	}
}
