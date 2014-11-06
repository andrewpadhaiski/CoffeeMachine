package by.epam.coffeemashine.controller.commands.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.coffeemashine.controller.commands.CommandException;
import by.epam.coffeemashine.controller.commands.ICommand;
import by.epam.coffeemashine.controller.constant.Attribute;
import by.epam.coffeemashine.controller.constant.Message;
import by.epam.coffeemashine.controller.constant.Page;
import by.epam.coffeemashine.model.data.entities.Coffee;
import by.epam.coffeemashine.model.data.entities.enums.UserRoleEnum;
import by.epam.coffeemashine.model.services.CoffeeService;
import by.epam.coffeemashine.model.services.exception.ServiceException;
import by.epam.coffeemashine.model.services.interfaces.ICoffeeService;
import by.epam.coffeemashine.util.MessageManager;

public class CoffeeCommand implements ICommand {

	private static final ICoffeeService COFFEE_SERVICE = CoffeeService.getInstance();

	@Override
	public String execute(HttpServletRequest request) throws CommandException {	
		
		String page = null;
		
		List<Coffee> coffees = null;
		try {
			coffees = COFFEE_SERVICE.getCoffees();
		} catch (ServiceException e) {
			throw new CommandException(e);
		}
		
		request.setAttribute(Attribute.COFFEES, coffees);	
		
		HttpSession session = request.getSession();
		UserRoleEnum role = (UserRoleEnum) session.getAttribute(Attribute.USER_ROLE);	
		
		switch(role) {
		case USER:
			page = Page.USER_COFFEE;
			return page;		
		case ADMIN:
			page = Page.ADMIN_COFFEE;
			return page;
		default:
			request.setAttribute(Attribute.WRONG_ACTION, 
					MessageManager.getMessage(Message.USER_ROLE_UNKNOWN));
			page = Page.LANDING;
			return page;
		}
	}
}
