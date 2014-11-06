package by.epam.coffeemashine.controller.commands.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.coffeemashine.controller.commands.CommandException;
import by.epam.coffeemashine.controller.commands.ICommand;
import by.epam.coffeemashine.controller.constant.Attribute;
import by.epam.coffeemashine.controller.constant.Message;
import by.epam.coffeemashine.controller.constant.Page;
import by.epam.coffeemashine.controller.constant.Parameter;
import by.epam.coffeemashine.model.data.entities.Coffee;
import by.epam.coffeemashine.model.data.entities.Ingredient;
import by.epam.coffeemashine.model.data.entities.Order;
import by.epam.coffeemashine.model.data.entities.User;
import by.epam.coffeemashine.model.services.IngredientService;
import by.epam.coffeemashine.model.services.OrderService;
import by.epam.coffeemashine.model.services.exception.ServiceException;
import by.epam.coffeemashine.model.services.interfaces.IIngredientService;
import by.epam.coffeemashine.model.services.interfaces.IOrderService;
import by.epam.coffeemashine.util.MessageManager;

public class UserOrderCommand implements ICommand {

	private static final IOrderService ORDER_SERVICE = OrderService
			.getInstance();
	private static final IIngredientService INGREDIENT_SERVICE = IngredientService
			.getInstance();

	@Override
	public String execute(HttpServletRequest request) throws CommandException {
		String page = null;

		HttpSession session = request.getSession();
		Coffee coffee = (Coffee) session.getAttribute(Attribute.COFFEE);
		User user = (User) session.getAttribute(Attribute.USER);

		Map<Integer, Integer> additions = new HashMap<>();

		String[] ingredientIds = request
				.getParameterValues(Parameter.INGREDIENT_ID);
		for (String ingredientId : ingredientIds) {
			String strQuantity = request.getParameter(ingredientId);
			if (!strQuantity.isEmpty()) {
				additions.put(Integer.valueOf(ingredientId),
						Integer.valueOf(strQuantity));
			}
		}

		Order order = null;
		try {
			order = ORDER_SERVICE.makeAnOrder(user, coffee, additions);
		} catch (ServiceException e) {
			throw new CommandException(e);
		}

		boolean present = false;

		try {
			present = INGREDIENT_SERVICE.checkPresence(order.getIngredients());
		} catch (ServiceException e) {
			throw new CommandException(e);
		}
		if (!present) {
			List<Ingredient> ingredients = null;
			try {
				ingredients = INGREDIENT_SERVICE.getIngredients();
			} catch (ServiceException e) {
				throw new CommandException(e);
			}
			request.setAttribute(Attribute.INGREDIENTS, ingredients);
			request.setAttribute(Attribute.ERROR_INGREDIENT_MESSAGE,
					MessageManager.getMessage(Message.INGREDIENTS_EMPTY));
			page = Page.USER_INGREDIENT;
			return page;
		}

		session.setAttribute(Attribute.ORDER, order);
		page = Page.USER_ORDER;
		return page;
	}
}
