package by.epam.coffeemashine.controller.commands.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import by.epam.coffeemashine.controller.commands.CommandException;
import by.epam.coffeemashine.controller.commands.ICommand;
import by.epam.coffeemashine.controller.constant.Attribute;
import by.epam.coffeemashine.controller.constant.Page;
import by.epam.coffeemashine.model.data.entities.Ingredient;
import by.epam.coffeemashine.model.services.IngredientService;
import by.epam.coffeemashine.model.services.exception.ServiceException;
import by.epam.coffeemashine.model.services.interfaces.IIngredientService;

public class AdminIngredientCommand implements ICommand {
	
	private static final IIngredientService INGREDIENT_SERVICE = IngredientService.getInstance();
	
	@Override
	public String execute(HttpServletRequest request) throws CommandException {
		
		String page = null;
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
