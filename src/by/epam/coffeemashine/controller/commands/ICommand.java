package by.epam.coffeemashine.controller.commands;

import javax.servlet.http.HttpServletRequest;

public interface ICommand {
	String execute(HttpServletRequest request) throws CommandException;
}
