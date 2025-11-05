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
    public T peek() throws EmptyQueueException;
    
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
     * Checks whether the queue is full.
     * 
     * @return False for dynamically sized books
     * 
     * <p><b>Precondition:</b> None.</p>
     * <p><b>Postcondition:</b> The state of the queue remains unchanged.</p>
     */
    public boolean isFull();

    /**
     * Returns the current number of elements in the queue.
     *
     * @return the number of elements stored in the queue
     *
     * <p><b>Precondition:</b> None.</p>
     * <p><b>Postcondition:</b> The queue remains unchanged.</p>
     */
    public int size();
    
    /**
     * Removes all elements from the queue.
     * 
     * <p><b>Precondition:</b> None.</p>
     * <p><b>Postcondition:</b> The queue is empty</p>
     */
    public void dequeueAll();
    
    /**
     * Checks if the queue contains the specified element.
     *
     * @param element the element to find
     * @return true if the queue contains the element, false otherwise
     * @throws NullPointerException if element is null
     */
    public boolean contains(T element);

    /**
     * Searches for the specified element in the queue and returns its 1-based position
     * from the front (head).
     *
     * @param element the element to find
     * @return integer that corresponds with the position of the element if found, -1 if not found
     */
    public int search(T element);

    /**
     * Shows all elements as an array containing in proper order
     * 
     * 
     * @return An array containing all the elements in the queue 
     */
    public Object[] toArray();
    
    /**
     * puts all elements into an array if the array big enough
     * 
     * 
     * @param array: the array with the limited size
     * @return the queue converted to the array
     */
    public <E> E[] toArray(E[] array); 
    
    /**
     * compares this queue to another queue for equality
     * 
     * @param other the other queue to compare with
     * @return True if both queues contain the same element in the same order
     */
    
    public boolean equals(QueueADT<T> other);
    
    /**
     * iterates the over the items in the queue
     * 
     * @return the iterator that iterates over the items
     */
    public Iterator<T> iterator();
    
}
