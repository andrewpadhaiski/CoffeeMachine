package by.epam.coffeemashine.controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.coffeemashine.controller.constant.Attribute;
import by.epam.coffeemashine.model.data.connection.ConnectionPoolException;

@WebFilter("/controller")
public class DbCapabilityFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		ServletContext context = httpRequest.getServletContext();
		ConnectionPoolException exception = (ConnectionPoolException) context
				.getAttribute(Attribute.ERROR_SERVER);

		if (exception != null) {
			throw new ServletException(exception);
		}
		chain.doFilter(httpRequest, httpResponse);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
