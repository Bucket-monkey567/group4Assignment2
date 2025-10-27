package utilities;

import exceptions.StackUnderflowException;

/**
 * The StackADT interface defines the standard behavior of a Last-In-First-Out (LIFO)
 * data structure, where elements are added and removed from the top of the stack.
 *
 * <p>This interface includes essential operations to check if the stack is empty and
 * to get its size. Exceptions for invalid operations are defined.</p>
 *
 * @param <T> the type of elements stored in the stack
 */
public interface StackADT<T> {

    /**
     * Checks whether the stack contains any elements.
     *
     * @return true if the stack has no elements; false otherwise
     *
     * <p><b>Precondition:</b> None.</p>
     * <p><b>Postcondition:</b> The state of the stack remains unchanged.</p>
     */
    boolean isEmpty();

    /**
     * Returns the number of elements currently stored in the stack.
     *
     * @return the number of elements in the stack
     *
     * <p><b>Precondition:</b> None.</p>
     * <p><b>Postcondition:</b> The state of the stack remains unchanged.</p>
     */
    int size(); //(Alex)
}
