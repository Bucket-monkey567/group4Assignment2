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
     * Adds (pushes) an element onto the top of the stack.
     *
     * @param element the element to be added to the top of the stack
     * @throws NullPointerException if the specified element is null
     *
     * <p><b>Precondition:</b> The stack is initialized and not full (if a capacity is defined).</p>
     * <p><b>Postcondition:</b> The element is added to the top of the stack.</p>
     */
    void push(T element) throws NullPointerException;

    /**
     * Removes (pops) and returns the element at the top of the stack.
     *
     * @return the element that was at the top of the stack
     * @throws StackUnderflowException if the stack is empty
     *
     * <p><b>Precondition:</b> The stack is not empty.</p>
     * <p><b>Postcondition:</b> The top element is removed and returned.</p>
     */
    T pop() throws StackUnderflowException;

    /**
     * Returns (but does not remove) the element currently at the top of the stack.
     *
     * @return the element at the top of the stack
     * @throws StackUnderflowException if the stack is empty
     *
     * <p><b>Precondition:</b> The stack is not empty.</p>
     * <p><b>Postcondition:</b> The stack remains unchanged.</p>
     */
    T top() throws StackUnderflowException;
    
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
