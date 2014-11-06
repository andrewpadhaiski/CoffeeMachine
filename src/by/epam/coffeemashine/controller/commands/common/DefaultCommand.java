package by.epam.coffeemashine.controller.commands.common;

import javax.servlet.http.HttpServletRequest;

import by.epam.coffeemashine.controller.commands.ICommand;
import by.epam.coffeemashine.controller.constant.Page;

public class DefaultCommand implements ICommand {

	@Override
	public String execute(HttpServletRequest request) {
		String page = Page.LANDING;
		return page;
	}
}
