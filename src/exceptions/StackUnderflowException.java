package exceptions;

/**
 * Exception thrown when an operation is attempted on an empty stack.
 */
public class StackUnderflowException extends Exception {

    /**
     * Default constructor with a standard message.
     */
    public StackUnderflowException() {
        super("Stack underflow: attempting to pop or peek from an empty stack.");
    }

    /**
     * Constructor with a custom message.
     * 
     * @param message custom error message
     */
    public StackUnderflowException(String message) {
        super(message);
    }
}
