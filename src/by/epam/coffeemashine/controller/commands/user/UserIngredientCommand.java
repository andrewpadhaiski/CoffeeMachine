package by.epam.coffeemashine.controller.commands.user;

import java.util.List;

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
import by.epam.coffeemashine.model.services.CoffeeService;
import by.epam.coffeemashine.model.services.IngredientService;
import by.epam.coffeemashine.model.services.exception.ServiceException;
import by.epam.coffeemashine.model.services.interfaces.ICoffeeService;
import by.epam.coffeemashine.model.services.interfaces.IIngredientService;
import by.epam.coffeemashine.util.MessageManager;

public class UserIngredientCommand implements ICommand {

	private static final ICoffeeService COFFEE_SERVICE = CoffeeService
			.getInstance();
	private static final IIngredientService INGREDIENT_SERVICE = IngredientService
			.getInstance();

	@Override
	public String execute(HttpServletRequest request) throws CommandException {
		String page = null;

		String strCoffeeId = request.getParameter(Parameter.COFFEE_ID);
		Integer coffeeId = Integer.valueOf(strCoffeeId);

		boolean present = false;

		try {
			present = COFFEE_SERVICE.checkPresence(coffeeId);
		} catch (ServiceException e) {
			throw new CommandException(e);
		}
		if (!present) {
			List<Coffee> coffees = null;
			try {
				coffees = COFFEE_SERVICE.getCoffees();
			} catch (ServiceException e) {
				throw new CommandException(e);
			}
			request.setAttribute(Attribute.COFFEES, coffees);
			request.setAttribute(Attribute.ERROR_COFFEE_MESSAGE,
					MessageManager.getMessage(Message.COFFEES_EMPTY));
			page = Page.USER_COFFEE;
			return page;
		}

		Coffee coffee = COFFEE_SERVICE.getCurrentCoffee();

		HttpSession session = request.getSession();
		session.setAttribute(Attribute.COFFEE, coffee);

		List<Ingredient> ingredients = null;
		try {
			ingredients = INGREDIENT_SERVICE.getIngredients();
		} catch (ServiceException e) {
			throw new CommandException(e);
		}

		request.setAttribute(Attribute.INGREDIENTS, ingredients);
		page = Page.USER_INGREDIENT;
		return page;
	}
}
