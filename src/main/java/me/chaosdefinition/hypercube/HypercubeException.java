package me.chaosdefinition.hypercube;

/**
 * The default exception in hypercube. This is just a simple wrapper of
 * {@link RuntimeException}.
 * 
 * @author Chaos Shen
 */
public class HypercubeException extends RuntimeException {

	private static final long serialVersionUID = -1102120552379762035L;

	/**
	 * Constructs a new {@link HypercubeException} with no message.
	 */
	public HypercubeException() {
		super();
	}

	/**
	 * Constructs a new {@link HypercubeException} with the specified detail
	 * message.
	 * 
	 * @param message
	 *            the detail message.
	 */
	public HypercubeException(String message) {
		super(message);
	}

	/**
	 * Constructs a new {@link HypercubeException} with the specified detail
	 * message and cause.
	 * 
	 * @param message
	 *            the detail message.
	 * @param cause
	 *            the cause of this exception
	 */
	public HypercubeException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Overrides the default {@link Throwable#printStackTrace()} and prints a
	 * shorter backtrace constisting only of causes and their messages (if
	 * exist) like the following.
	 * <pre>
	 * hypercube: Message of this exception.
	 * Caused by: java.lang.NullPointerException
	 * Caused by: java.lang.IOException: File not found.
	 * ...
	 * </pre>
	 */
	@Override
	public void printStackTrace() {
		System.err.println("hypercube: " + getMessage());
		Throwable cause = getCause();
		while (cause != null) {
			System.err.println("Caused by: " + cause);
			cause = cause.getCause();
		}
	}
}
