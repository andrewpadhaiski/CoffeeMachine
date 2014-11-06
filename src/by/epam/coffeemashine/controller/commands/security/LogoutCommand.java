package by.epam.coffeemashine.controller.commands.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.coffeemashine.controller.commands.ICommand;
import by.epam.coffeemashine.controller.constant.Attribute;
import by.epam.coffeemashine.controller.constant.Page;
import by.epam.coffeemashine.model.data.entities.enums.UserRoleEnum;
import by.epam.coffeemashine.util.MessageManager;

public class LogoutCommand implements ICommand {

	private static final String LANGUAGE = "en";
	
	@Override
	public String execute(HttpServletRequest request) {
		String page = Page.LANDING;
		HttpSession session = request.getSession();
		session.invalidate();	
		
		session = request.getSession();
		session.setAttribute(Attribute.USER_ROLE, UserRoleEnum.GUEST);
		MessageManager.setLanguage(LANGUAGE);
		return page;
	}

}
