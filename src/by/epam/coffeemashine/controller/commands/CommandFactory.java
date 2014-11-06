package by.epam.coffeemashine.controller.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.coffeemashine.controller.commands.common.DefaultCommand;
import by.epam.coffeemashine.controller.constant.Attribute;
import by.epam.coffeemashine.controller.constant.Message;
import by.epam.coffeemashine.controller.constant.Parameter;
import by.epam.coffeemashine.model.data.entities.enums.UserRoleEnum;
import by.epam.coffeemashine.util.MessageManager;

public class CommandFactory {

	public ICommand defineCommand(HttpServletRequest request) {
		ICommand command = null;
		String strCommand = request.getParameter(Parameter.COMMAND);
		
		if (strCommand == null || strCommand.isEmpty()) {
			command = new DefaultCommand();
			return command;
		}
		try {
			CommandEnum currentEnum = CommandEnum.valueOf(strCommand
					.toUpperCase());
			UserRoleEnum commandRole = currentEnum.getUserRole();
			HttpSession session = request.getSession();
			UserRoleEnum currentRole = (UserRoleEnum) session
					.getAttribute(Attribute.USER_ROLE);
			
			if (currentRole.ordinal() >= commandRole.ordinal()) {
				command = currentEnum.getCurrentCommand();
			} else {
				command = new DefaultCommand();
				request.setAttribute(Attribute.WRONG_ACTION,
						MessageManager.getMessage(Message.ACCESS_DENIED));
			}

		} catch (IllegalArgumentException e) {
			request.setAttribute(Attribute.WRONG_ACTION,
					MessageManager.getMessage(Message.WRONG_ACTION));
		}
		return command;
	}
}
