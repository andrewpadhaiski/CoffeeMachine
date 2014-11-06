package by.epam.coffeemashine.controller.commands.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.coffeemashine.controller.commands.CommandException;
import by.epam.coffeemashine.controller.commands.ICommand;
import by.epam.coffeemashine.controller.constant.Attribute;
import by.epam.coffeemashine.controller.constant.Message;
import by.epam.coffeemashine.controller.constant.Page;
import by.epam.coffeemashine.model.data.entities.Order;
import by.epam.coffeemashine.model.data.entities.User;
import by.epam.coffeemashine.model.services.OrderService;
import by.epam.coffeemashine.model.services.exception.ServiceException;
import by.epam.coffeemashine.model.services.interfaces.IOrderService;
import by.epam.coffeemashine.util.MessageManager;

public class UserConfirmCommand implements ICommand {
	
	private static final IOrderService ORDERS = OrderService.getInstance();
	
	@Override
	public String execute(HttpServletRequest request) throws CommandException {
		String page = null;
	
		HttpSession session = request.getSession();
		Order order = (Order) session.getAttribute(Attribute.ORDER);
		
		boolean success = false;
		try {
			success = ORDERS.transact(order);
		} catch (ServiceException e) {
			throw new CommandException(e);
		}
		if(!success) {
			request.setAttribute(Attribute.ERROR_ORDER_MESSAGE, 
					MessageManager.getMessage(Message.AMOUNT_BIG));
			page = Page.USER_ORDER;
			return page;			
		}
		
		User user = (User) session.getAttribute(Attribute.USER);
		user.setBalance(user.getBalance().subtract(order.getAmount()));
		
		page = Page.USER_THANKS;
		return page;
	}

}
