package by.epam.coffeemashine.controller.commands.account;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.coffeemashine.controller.commands.CommandException;
import by.epam.coffeemashine.controller.commands.ICommand;
import by.epam.coffeemashine.controller.constant.Attribute;
import by.epam.coffeemashine.controller.constant.Message;
import by.epam.coffeemashine.controller.constant.Page;
import by.epam.coffeemashine.controller.constant.Parameter;
import by.epam.coffeemashine.controller.constant.Regex;
import by.epam.coffeemashine.model.data.entities.User;
import by.epam.coffeemashine.model.services.AccountService;
import by.epam.coffeemashine.model.services.exception.ServiceException;
import by.epam.coffeemashine.model.services.interfaces.IAccountService;
import by.epam.coffeemashine.util.MessageManager;

public class WithdrawCommand implements ICommand {

	private static final IAccountService ACCOUNT = AccountService.getInstance();

	@Override
	public String execute(HttpServletRequest request) throws CommandException {
		String page = null;
		String strAmount = request.getParameter(Parameter.AMOUNT).trim();

		if (!strAmount.matches(Regex.DOUBLE)) {
			request.setAttribute(Attribute.ERROR_WITHDRAW_MESSAGE, 
					MessageManager.getMessage(Message.INVALID_VALUE));
			page = Page.PROFILE;
			return page;
		}

		BigDecimal amount = new BigDecimal(strAmount);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Attribute.USER);
		
		boolean success = false;
		try {
			success = ACCOUNT.withdraw(user, amount);
		} catch (ServiceException e) {
			throw new CommandException(e);
		}
		
		if(!success) {
			request.setAttribute(Attribute.ERROR_WITHDRAW_MESSAGE,
					MessageManager.getMessage(Message.AMOUNT_BIG));
			page = Page.PROFILE;
			return page;
		}
		
		page = Page.PROFILE;
		return page;
	}
}
