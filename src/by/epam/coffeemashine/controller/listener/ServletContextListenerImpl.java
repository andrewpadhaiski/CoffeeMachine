package by.epam.coffeemashine.controller.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.coffeemashine.controller.constant.Attribute;
import by.epam.coffeemashine.model.data.connection.ConnectionPool;
import by.epam.coffeemashine.model.data.connection.ConnectionPoolException;
import by.epam.coffeemashine.model.data.connection.IConnectionPool;

@WebListener
public class ServletContextListenerImpl implements ServletContextListener {

	private static final Logger LOGGER = LogManager
			.getLogger(ServletContextListenerImpl.class.getName());

	private static final IConnectionPool POOL = ConnectionPool.getInstance();

	public void contextDestroyed(ServletContextEvent ev) {
		try {
			POOL.closePool();
		} catch (ConnectionPoolException e) {
			LOGGER.error(e);
		}
	}

	public void contextInitialized(ServletContextEvent ev) {
		try {
			POOL.initPool();
		} catch (ConnectionPoolException e) {	
			LOGGER.error(e);
			ev.getServletContext()
					.setAttribute(Attribute.ERROR_SERVER, e);
		}
	}
}
