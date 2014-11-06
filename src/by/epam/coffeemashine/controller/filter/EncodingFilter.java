package by.epam.coffeemashine.controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import by.epam.coffeemashine.util.ConfigurationManager;

@WebFilter("/*")
public class EncodingFilter implements Filter {

	private static final String ENCODING = ConfigurationManager.getProperty("servlet.encoding");
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String codeRequest = request.getCharacterEncoding();
		if(codeRequest == null || !codeRequest.equalsIgnoreCase(ENCODING)) {
			request.setCharacterEncoding(ENCODING);
			response.setCharacterEncoding(ENCODING);
		};	
		chain.doFilter(request, response);
	}
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
