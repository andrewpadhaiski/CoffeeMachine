package by.epam.coffeemashine.controller.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.coffeemashine.controller.commands.CommandException;
import by.epam.coffeemashine.controller.commands.CommandFactory;
import by.epam.coffeemashine.controller.commands.ICommand;
import by.epam.coffeemashine.controller.constant.Attribute;
import by.epam.coffeemashine.controller.constant.Message;
import by.epam.coffeemashine.controller.constant.Page;

@WebServlet("/controller")
public class CoffeeServlet extends HttpServlet {
	
	private static final long serialVersionUID = -122127449796243061L;

	private static final Logger LOGGER = LogManager
			.getLogger(CoffeeServlet.class.getName());

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		CommandFactory factory = new CommandFactory();
		ICommand command = factory.defineCommand(request);

		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = null;
		
		try {
			page = command.execute(request);
		} catch (CommandException e) {
			LOGGER.error(e);
			throw new ServletException(e);
		}

		if (page != null) {
			dispatcher = context.getRequestDispatcher(page);
			dispatcher.forward(request, response);
		} else {
			page = Page.INDEX;
			dispatcher = context.getRequestDispatcher(page);
			HttpSession session = request.getSession();
			session.setAttribute(Attribute.NULL_PAGE, Message.NULL_PAGE);
			response.sendRedirect(request.getContextPath() + Page.INDEX);
		}
	}
}
