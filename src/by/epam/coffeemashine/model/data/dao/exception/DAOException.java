package by.epam.coffeemashine.model.data.dao.exception;

public class DAOException extends Exception {
	private static final long serialVersionUID = -1961959957633792988L;

	public DAOException() {
		super();
	}

	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	public DAOException(String message) {
		super(message);
	}

	public DAOException(Throwable cause) {
		super(cause);
	}
}
