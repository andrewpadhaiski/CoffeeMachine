package by.epam.coffeemashine.controller.commands;

public class CommandException extends Exception {

	private static final long serialVersionUID = -2927977286319404765L;

	public CommandException() {
		super();
	}

	public CommandException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommandException(String message) {
		super(message);
	}

	public CommandException(Throwable cause) {
		super(cause);
	}

}
