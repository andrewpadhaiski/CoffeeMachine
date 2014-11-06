package by.epam.coffeemashine.model.data.connection;

public class ConnectionPoolException extends Exception {

	private static final long serialVersionUID = 3048399076455317757L;

	public ConnectionPoolException() {
		super();
	}

	public ConnectionPoolException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConnectionPoolException(String message) {
		super(message);
	}

	public ConnectionPoolException(Throwable cause) {
		super(cause);
	}
}
