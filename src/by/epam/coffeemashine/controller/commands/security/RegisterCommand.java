package by.epam.coffeemashine.controller.commands.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.coffeemashine.controller.commands.CommandException;
import by.epam.coffeemashine.controller.commands.ICommand;
import by.epam.coffeemashine.controller.constant.Attribute;
import by.epam.coffeemashine.controller.constant.Message;
import by.epam.coffeemashine.controller.constant.Page;
import by.epam.coffeemashine.controller.constant.Parameter;
import by.epam.coffeemashine.model.data.entities.User;
import by.epam.coffeemashine.model.data.entities.enums.UserRoleEnum;
import by.epam.coffeemashine.model.services.SecurityService;
import by.epam.coffeemashine.model.services.exception.ServiceException;
import by.epam.coffeemashine.model.services.interfaces.ISecurityService;
import by.epam.coffeemashine.util.MessageManager;

public class RegisterCommand implements ICommand {

	private static final ISecurityService SECURITY = SecurityService.getInstance();
	
	@Override
	public String execute(HttpServletRequest request) throws CommandException {
		String page = null;
		
		String enterName = request.getParameter(Parameter.USERNAME);
		String enterLogin = request.getParameter(Parameter.LOGIN);
		String enterPass = request.getParameter(Parameter.PASSWORD);
		String repeatPass = request.getParameter(Parameter.REPEATED_PASSWORD);
		
		if (enterName.isEmpty() || enterLogin.isEmpty() || enterPass.isEmpty()
				|| repeatPass.isEmpty()) {
			request.setAttribute(Attribute.ERROR_REGISTER_MESSAGE, 
					MessageManager.getMessage(Message.EMPTY_FIELDS));
			page = Page.LANDING;
			return page;
		}		
		
		if (!enterPass.equals(repeatPass)) {
			request.setAttribute(Attribute.ERROR_REGISTER_MESSAGE, 
					MessageManager.getMessage(Message.PASSWORDS_MISMATCH));
			page = Page.LANDING;
			return page;
		}
		
		boolean success = false;
		try {
			success = SECURITY.register(enterName, enterLogin, enterPass);
		} catch (ServiceException e) { 
			throw new CommandException(e);
		}
		
		if(!success) {
			request.setAttribute(Attribute.ERROR_REGISTER_MESSAGE, 
					MessageManager.getMessage(Message.USER_EXISTS));
			page = Page.LANDING;
			return page;
		}
		
		User user = SECURITY.getCurrentUser();
		
		HttpSession session = request.getSession();
		session.setAttribute(Attribute.USER, user);
		
		UserRoleEnum role = user.getRole();
		session.setAttribute(Attribute.USER_ROLE, role);
		
		page = Page.PROFILE;
		return page;
	}
}
