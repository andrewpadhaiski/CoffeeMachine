package by.epam.coffeemashine.model.services.exception;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 7577039713051525666L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}
}
