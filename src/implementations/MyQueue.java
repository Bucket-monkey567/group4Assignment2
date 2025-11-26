package implementations;

import exceptions.EmptyQueueException;
import utilities.Iterator;
import utilities.QueueADT;

/**
 * Linked-list based Queue implementation.
 *
 * @param <e> the type of elements stored in the queue
 */
public class MyQueue<e> implements QueueADT<e> {

    /**
     * Node class representing an element in the linked list.
     */
    private class Node {
        e data;
        Node next;

        Node(e data) {
            this.data = data;
        }
    }

    private Node front;
    private Node rear;
    private int size = 0;

    /**
     * Copy constructor — creates a new queue containing the same elements
     * as another queue.
     *
     * @param other the queue to copy from
     * @throws NullPointerException if the provided queue is null
     */
    public MyQueue(QueueADT<e> other) throws NullPointerException {
        if (other == null)
            throw new NullPointerException("Cannot copy from a null queue");

        this.front = null;
        this.rear = null;
        this.size = 0;

        Iterator<e> it = other.iterator();
        while (it.hasNext()) {
            this.enqueue(it.next());
        }
    }

    /**
     * Creates an empty queue.
     */
    public MyQueue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    /**
     * Adds an element to the rear of the queue.
     *
     * @param toAdd the element to be added
     * @throws NullPointerException if toAdd is null
     */
    @Override
    public void enqueue(e toAdd) throws NullPointerException {
        if (toAdd == null)
            throw new NullPointerException("Cannot enqueue null");

        Node newNode = new Node(toAdd);

        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }

        size++;
    }

    /**
     * Removes and returns the element at the front of the queue.
     *
     * @return the removed element
     * @throws EmptyQueueException if the queue is empty
     */
    @Override
    public e dequeue() throws EmptyQueueException {
        if (isEmpty())
            throw new EmptyQueueException("Queue is empty");

        e removed = front.data;
        front = front.next;

        if (front == null)
            rear = null;

        size--;
        return removed;
    }

    /**
     * Returns the element at the front of the queue without removing it.
     *
     * @return the front element
     * @throws EmptyQueueException if the queue is empty
     */
    @Override
    public e peek() throws EmptyQueueException {
        if (isEmpty())
            throw new EmptyQueueException("Queue is empty");

        return front.data;
    }

    /**
     * Removes all elements from the queue.
     */
    @Override
    public void dequeueAll() {
        front = null;
        rear = null;
        size = 0;
    }

    /**
     * Checks whether the queue is empty.
     *
     * @return true if empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Checks if the queue contains the given element.
     *
     * @param toFind the element to search for
     * @return true if found, false otherwise
     * @throws NullPointerException if toFind is null
     */
    @Override
    public boolean contains(e toFind) throws NullPointerException {
        if (toFind == null)
            throw new NullPointerException("Cannot search for null");

        Node current = front;
        while (current != null) {
            if (current.data.equals(toFind))
                return true;
            current = current.next;
        }

        return false;
    }

    /**
     * Searches for an element and returns its 1-based position.
     *
     * @param toFind the element to search for
     * @return position of element, or -1 if not found
     */
    @Override
    public int search(e toFind) {
        if (toFind == null)
            return -1;

        Node current = front;
        int index = 1;

        while (current != null) {
            if (current.data.equals(toFind))
                return index;

            current = current.next;
            index++;
        }
        return -1;
    }

    /**
     * Returns an iterator over the queue in proper order.
     *
     * @return an iterator over the queue elements
     */
    @Override
    public Iterator<e> iterator() {
        return new Iterator<e>() {
            Node current = front;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public e next() {
                if (current == null)
                    throw new java.util.NoSuchElementException("No more elements in queue");

                e data = current.data;
                current = current.next;
                return data;
            }
        };
    }

    /**
     * Checks if this queue is equal to another queue.
     *
     * @param that the queue to compare with
     * @return true if same size and same element order
     */
    @Override
    public boolean equals(QueueADT<e> that) {
        if ((that == null) || (this.size != that.size()))
            return false;

        Iterator<e> it1 = this.iterator();
        Iterator<e> it2 = that.iterator();

        while (it1.hasNext() && it2.hasNext()) {
            e a = it1.next();
            e b = it2.next();
            if (!a.equals(b))
                return false;
        }

        return true;
    }

    /**
     * Converts the queue into an Object array.
     *
     * @return the created array
     */
    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size];

        int i = 0;
        for (Node current = front; current != null; current = current.next, i++) {
            arr[i] = current.data;
        }

        return arr;
    }

    /**
     * Copies queue elements into a provided array.
     *
     * @param holder the array to write into
     * @return the filled array
     * @throws NullPointerException if holder is null
     */
    @Override
    public e[] toArray(e[] holder) throws NullPointerException {
        if (holder == null)
            throw new NullPointerException("Holder array cannot be null");

        if (holder.length < size) {
            holder = (e[]) java.lang.reflect.Array.newInstance(
                holder.getClass().getComponentType(), size);
        }

        int i = 0;
        for (Node current = front; current != null; current = current.next, i++) {
            holder[i] = current.data;
        }

        return holder;
    }

    /**
     * Checks if the queue is full.
     * Linked-list queues can never be full.
     *
     * @return false (always)
     */
    @Override
    public boolean isFull() {
        return false;
    }

    /**
     * Returns the number of elements in the queue.
     *
     * @return queue size
     */
    @Override
    public int size() {
        return size;
    }
}
