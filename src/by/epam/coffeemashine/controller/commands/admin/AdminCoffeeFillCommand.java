package by.epam.coffeemashine.controller.commands.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import by.epam.coffeemashine.controller.commands.CommandException;
import by.epam.coffeemashine.controller.commands.ICommand;
import by.epam.coffeemashine.controller.constant.Attribute;
import by.epam.coffeemashine.controller.constant.Message;
import by.epam.coffeemashine.controller.constant.Page;
import by.epam.coffeemashine.controller.constant.Parameter;
import by.epam.coffeemashine.model.data.entities.Coffee;
import by.epam.coffeemashine.model.services.CoffeeService;
import by.epam.coffeemashine.model.services.exception.ServiceException;
import by.epam.coffeemashine.model.services.interfaces.ICoffeeService;
import by.epam.coffeemashine.util.MessageManager;

public class AdminCoffeeFillCommand implements ICommand {

	private static final ICoffeeService COFFEE_SERVICE = CoffeeService
			.getInstance();

	@Override
	public String execute(HttpServletRequest request) throws CommandException {

		String page = null;
		String coffeeId = request.getParameter(Parameter.COFFEE_ID);
		String quantity = request.getParameter(Parameter.QUANTITY);
		if (quantity.isEmpty()) {
			request.setAttribute(Attribute.ERROR_COFFEE_MESSAGE,
					MessageManager.getMessage(Message.COFFEE_ZERO_QUANTITY));

			List<Coffee> coffees = null;
			try {
				coffees = COFFEE_SERVICE.getCoffees();
			} catch (ServiceException e) {
				throw new CommandException(e);
			}
			request.setAttribute(Attribute.COFFEES, coffees);

			page = Page.ADMIN_COFFEE;
			return page;
		}
		try {
			COFFEE_SERVICE.fill(Integer.valueOf(coffeeId),
					Integer.valueOf(quantity));
		} catch (ServiceException e) {
			throw new CommandException(e);
		}

		List<Coffee> coffees = null;
		try {
			coffees = COFFEE_SERVICE.getCoffees();
		} catch (ServiceException e) {
			throw new CommandException(e);
		}
		request.setAttribute(Attribute.COFFEES, coffees);
		page = Page.ADMIN_COFFEE;
		return page;

	}
}
