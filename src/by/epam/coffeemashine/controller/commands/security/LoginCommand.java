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

public class LoginCommand implements ICommand {

	private static final ISecurityService SECURITY = SecurityService
			.getInstance();

	@Override
	public String execute(HttpServletRequest request) throws CommandException {
		String page = null;

		String enterLogin = request.getParameter(Parameter.LOGIN);
		String enterPass = request.getParameter(Parameter.PASSWORD);

		if (enterLogin.isEmpty() || enterPass.isEmpty()) {
			request.setAttribute(Attribute.ERROR_LOGIN_MESSAGE, 
					MessageManager.getMessage(Message.EMPTY_FIELDS));
			page = Page.LANDING;
			return page;
		}
			
		boolean success = false;
		try {
			success = SECURITY.login(enterLogin, enterPass);
		} catch (ServiceException e) {
			throw new CommandException(e);
		}
		if (!success) {
			request.setAttribute(Attribute.ERROR_LOGIN_MESSAGE, 
					MessageManager.getMessage(Message.LOGIN_PASSWORD_INCORRECT));
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
