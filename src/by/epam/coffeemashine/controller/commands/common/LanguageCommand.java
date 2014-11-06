package by.epam.coffeemashine.controller.commands.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.coffeemashine.controller.commands.CommandException;
import by.epam.coffeemashine.controller.commands.ICommand;
import by.epam.coffeemashine.controller.constant.Attribute;
import by.epam.coffeemashine.controller.constant.Page;
import by.epam.coffeemashine.controller.constant.Parameter;
import by.epam.coffeemashine.model.data.entities.enums.UserRoleEnum;
import by.epam.coffeemashine.util.MessageManager;

public class LanguageCommand implements ICommand {
	
	@Override
	public String execute(HttpServletRequest request) throws CommandException {
		String page = null;

		String language = request.getParameter(Parameter.LANGUAGE);
		MessageManager.setLanguage(language);
		
		HttpSession session = request.getSession();		
		session.invalidate();
		
		session = request.getSession();		
		session.setAttribute(Attribute.USER_ROLE, UserRoleEnum.GUEST);
		session.setAttribute(Attribute.LANGUAGE, language);
		
		page = Page.LANDING;
		return page;
	}

}
