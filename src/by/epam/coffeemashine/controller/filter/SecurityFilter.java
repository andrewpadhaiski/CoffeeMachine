package by.epam.coffeemashine.controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.coffeemashine.controller.constant.Attribute;
import by.epam.coffeemashine.controller.constant.Page;
import by.epam.coffeemashine.model.data.entities.enums.UserRoleEnum;

@WebFilter("/controller")
public class SecurityFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		HttpSession session = httpRequest.getSession();
		UserRoleEnum role = (UserRoleEnum) session.getAttribute(Attribute.USER_ROLE);
		
		if(role == null) {
			role = UserRoleEnum.GUEST;
			session.setAttribute(Attribute.USER_ROLE, role);
			RequestDispatcher dispatcher = httpRequest
					.getRequestDispatcher(Page.LANDING);
			dispatcher.forward(httpRequest, httpResponse);
			return;
		}
		chain.doFilter(request, response);
	}
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
