package utilities;

import exceptions.StackUnderflowException;
import java.util.Iterator;

/**
 * The StackADT interface defines the standard behavior of a Last-In-First-Out (LIFO)
 * data structure, where elements are added and removed from the top of the stack.
 *
 * <p>This interface includes essential operations to check if the stack is empty and
 * to get its size. Exceptions for invalid operations are defined.</p>
 *
 * @param <T> the type of elements stored in the stack
 */
public interface StackADT<T> extends Iterable<T> {

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
    
    /**
     * Removes all elements from the stack, leaving it empty.
     *
     * <p><b>Postcondition:</b> The stack is empty.</p>
     */
    void clear();
    
    /**
     * Determines whether this stack contains a specific element.
     *
     * @param obj the element to search for
     * @return true if the element exists in the stack; false otherwise
     */
    boolean contains(T obj);
    
    /**
     * Searches for an element in the stack and returns its 1-based position
     * from the top of the stack. The top element has position 1.
     *
     * @param obj the element to search for
     * @return the 1-based position of the element, or -1 if not found
     */
    int search(T obj);
    
    /**
     * Returns an array containing all of the elements in this stack.
     * The top of the stack corresponds to the first element of the array.
     *
     * @return an array containing all of the elements in this stack
     */
    Object[] toArray();
    
    /**
     * Returns an array containing all of the elements in this stack,
     * using the provided array if it fits.
     * The top of the stack corresponds to the first element of the array.
     *
     * @param copy the array into which the elements will be stored, if it is large enough
     * @return an array containing all of the elements in this stack
     */
    T[] toArray(T[] copy);
    
    /**
     * Returns an iterator over the elements in this stack.
     * The iterator does not support the remove operation.
     *
     * @return an iterator over the stack elements
     */
    @Override
    Iterator<T> iterator();
    

    /**
     * Compares this stack with another stack for equality.
     * Two stacks are considered equal if they contain the same elements
     * in the same order (from top to bottom).
     *
     * @param that the other stack to compare
     * @return true if both stacks are equal; false otherwise
     */
    boolean equals(StackADT<T> that);
}
