package by.epam.coffeemashine.controller.commands.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import by.epam.coffeemashine.controller.commands.CommandException;
import by.epam.coffeemashine.controller.commands.ICommand;
import by.epam.coffeemashine.controller.constant.Attribute;
import by.epam.coffeemashine.controller.constant.Message;
import by.epam.coffeemashine.controller.constant.Page;
import by.epam.coffeemashine.controller.constant.Parameter;
import by.epam.coffeemashine.model.data.entities.Ingredient;
import by.epam.coffeemashine.model.services.IngredientService;
import by.epam.coffeemashine.model.services.exception.ServiceException;
import by.epam.coffeemashine.model.services.interfaces.IIngredientService;
import by.epam.coffeemashine.util.MessageManager;

public class AdminIngredientFillCommand implements ICommand {
	private static final IIngredientService INGREDIENT_SERVICE = IngredientService
			.getInstance();

	@Override
	public String execute(HttpServletRequest request) throws CommandException {

		String page = null;
		String ingredientId = request.getParameter(Parameter.INGREDIENT_ID);
		String quantity = request.getParameter(Parameter.QUANTITY);
		if (quantity.isEmpty()) {
			request.setAttribute(Attribute.ERROR_INGREDIENT_MESSAGE,
					MessageManager.getMessage(Message.INGREDIENT_ZERO_QUANTITY));
			List<Ingredient> ingredients = null;
			try {
				ingredients = INGREDIENT_SERVICE.getIngredients();
			} catch (ServiceException e) {
				throw new CommandException(e);
			}
			request.setAttribute(Attribute.INGREDIENTS, ingredients);
			page = Page.ADMIN_INGREDIENT;
			return page;
		}
		try {
			INGREDIENT_SERVICE.fill(Integer.valueOf(ingredientId),
					Integer.valueOf(quantity));
		} catch (ServiceException e) {
			throw new CommandException(e);
		}

		List<Ingredient> ingredients = null;
		try {
			ingredients = INGREDIENT_SERVICE.getIngredients();
		} catch (ServiceException e) {
			throw new CommandException(e);
		}

		request.setAttribute(Attribute.INGREDIENTS, ingredients);
		page = Page.ADMIN_INGREDIENT;
		return page;
	}
}
