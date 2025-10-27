package utilities;

import exceptions.EmptyQueueException;

/**
 * The QueueADT interface defines the standard behavior of a First-In-First-Out (FIFO)
 * data structure, where elements are added to the rear and removed from the front.
 *
 * <p>This interface provides essential operations to interact with a queue, such as 
 * checking its size, determining if it is empty, and retrieving the front element 
 * without removing it.</p>
 *
 * @param <T> the type of elements stored in the queue
 */
public interface QueueADT<T> {
	
	/**
	 * Adds an element to the end of the queue
	 * <p><b>Precondition:</b> Element must not be null.</p>
     * <p><b>Postcondition:</b> Queue has a a new element at the rear</p>
	 */
	public void enqueue(T element);
	
	/**
	 * Retrieves and removes the front element of the queue
     *
     * @return The removed element
     *
     * @throws EmptyQueueException if the queue is empty
     *
     * <p><b>Precondition:</b> The queue must contain at least one element.</p>
     * <p><b>Postcondition:</b> the front element of the queue is removed.</p>
	 */
	public T dequeue() throws EmptyQueueException;

    /**
     * Retrieves (but does not remove) the element at the front of the queue.
     *
     * @return the front element of the queue
     *
     * @throws EmptyQueueException if the queue is empty
     *
     * <p><b>Precondition:</b> The queue must contain at least one element.</p>
     * <p><b>Postcondition:</b> The queue remains unchanged.</p>
     */
    public T getFront() throws EmptyQueueException;
    
    /**
     * Retrieves (but does not remove) the element at the rear of the queue.
     * @return the rear element f the queue
     * @throws EmptyQueueException
     * 
     * <p><b>Precondition:</b> The queue must contain at least one element.</p>
     * <p><b>Postcondition:</b> The queue remains unchanged..</p>
     */
    public T getRear() throws EmptyQueueException;

    /**
     * Checks whether the queue contains any elements.
     *
     * @return true if the queue has no elements; false otherwise
     *
     * <p><b>Precondition:</b> None.</p>
     * <p><b>Postcondition:</b> The state of the queue remains unchanged.</p>
     */
    public boolean isEmpty();

    /**
     * Returns the current number of elements in the queue.
     *
     * @return the number of elements stored in the queue
     *
     * <p><b>Precondition:</b> None.</p>
     * <p><b>Postcondition:</b> The queue remains unchanged.</p>
     */
    public int size();
}
