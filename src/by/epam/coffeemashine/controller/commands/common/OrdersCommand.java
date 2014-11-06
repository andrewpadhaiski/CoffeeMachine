package by.epam.coffeemashine.controller.commands.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.coffeemashine.controller.commands.CommandException;
import by.epam.coffeemashine.controller.commands.ICommand;
import by.epam.coffeemashine.controller.constant.Attribute;
import by.epam.coffeemashine.controller.constant.Message;
import by.epam.coffeemashine.controller.constant.Page;
import by.epam.coffeemashine.controller.constant.Parameter;
import by.epam.coffeemashine.model.data.entities.Order;
import by.epam.coffeemashine.model.data.entities.User;
import by.epam.coffeemashine.model.data.entities.enums.UserRoleEnum;
import by.epam.coffeemashine.model.services.OrderService;
import by.epam.coffeemashine.model.services.exception.ServiceException;
import by.epam.coffeemashine.model.services.interfaces.IOrderService;
import by.epam.coffeemashine.util.MessageManager;

public class OrdersCommand implements ICommand {

	private static final IOrderService ORDERS = OrderService.getInstance();
	private static final int ORDERS_PER_PAGE = 3;

	@Override
	public String execute(HttpServletRequest request) throws CommandException {
		String page = null;

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Attribute.USER);

		List<Order> orders = null;

		UserRoleEnum role = user.getRole();

		switch (role) {
		case USER:
			try {
				orders = ORDERS.getOrdersByUser(user);
			} catch (ServiceException e) {
				throw new CommandException(e);
			}
			page = Page.USER_ORDERS;
			break;
		case ADMIN:
			try {
				orders = ORDERS.getOrders();

			} catch (ServiceException e) {
				throw new CommandException(e);
			}
			page = Page.ADMIN_ORDERS;
			break;
		default:
			request.setAttribute(Attribute.ERROR_LOGIN_MESSAGE,
					MessageManager.getMessage(Message.USER_ROLE_UNKNOWN));
			page = Page.LANDING;
			return page;
		}

		int currentPage = 1;
		int numberOfOrders = orders.size();
		if (numberOfOrders > ORDERS_PER_PAGE) {
			int numberOfPages = (int) Math.ceil(numberOfOrders * 1.0
					/ ORDERS_PER_PAGE);
			if (request.getParameter(Parameter.CURRENT_PAGE) != null) {
				currentPage = Integer.parseInt(request
						.getParameter(Parameter.CURRENT_PAGE));
			}
			int fromIndex = (currentPage - 1) * ORDERS_PER_PAGE;
			int toIndex = fromIndex + ORDERS_PER_PAGE;
			int temp = numberOfOrders - fromIndex;
			if (temp < ORDERS_PER_PAGE) {
				toIndex = fromIndex + temp;
			}
			orders = orders.subList(fromIndex, toIndex);
			request.setAttribute(Attribute.NUMBER_OF_PAGES, numberOfPages);
		}
		request.setAttribute(Attribute.CURRENT_PAGE, currentPage);
		request.setAttribute(Attribute.ORDERS, orders);
		return page;
	}
}
