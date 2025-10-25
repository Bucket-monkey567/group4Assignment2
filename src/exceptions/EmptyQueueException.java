package exceptions;

/**
 * Exception thrown to indicate that an operation requiring one or more
 * elements in the queue was attempted on an empty queue.
 *
 * <p>This can occur, for example, when attempting to retrieve or remove an
 * element from the front of a queue that currently contains no elements.</p>
 */
public class EmptyQueueException extends Exception {

    /**
     * Constructs a new EmptyQueueException with the default error message
     * "Queue is empty".
     */
    public EmptyQueueException() {
        super("Queue is empty");
    }
    
    /**
     * Constructs a new EmptyQueueException with a custom error message.
     *
     * @param message the detail message explaining the cause of the exception.
     */
    public EmptyQueueException(String message) {
        super(message);
    }
}
