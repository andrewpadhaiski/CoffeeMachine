package by.epam.coffeemashine.view;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import by.epam.coffeemashine.controller.constant.Message;
import by.epam.coffeemashine.util.MessageManager;

public class ErrorTag extends TagSupport {

	private static final long serialVersionUID = -6696233572228712594L;

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		String uri = pageContext.getErrorData().getRequestURI();
		int statusCode = pageContext.getErrorData().getStatusCode();
		Exception exception = pageContext.getException();
		String exceptionMessage = exception.getMessage();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append(MessageManager.getMessage(Message.REQUEST)).append(uri).append("<br>");
		buffer.append(MessageManager.getMessage(Message.STATUS_CODE)).append(statusCode).append("<br>");
		buffer.append(MessageManager.getMessage(Message.EXCEPTION_MESSAGE)).append(exceptionMessage).append("<br>");	
		
		JspWriter out = pageContext.getOut();
		try {
			out.write(buffer.toString());
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		return SKIP_BODY;
	}

}
