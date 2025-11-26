package implementations;

/**
 * Node used by {@link MyDLL}. Stores an element and links to previous and next nodes.
 * <p>
 * This is a simple container class used internally by {@code MyDLL}. Each node
 * holds a reference to an element of type {@code E} and references to the
 * previous and next nodes in the doubly-linked list.
 * </p>
 *
 * @param <E> element type stored in the node
 */
public class MyDLLNode<E> {
	// The value stored in this node.
    private E element;
    // Reference to the next node in the list (toward the tail).
    private MyDLLNode<E> next;
    // Reference to the previous node in the list (toward the head).
    private MyDLLNode<E> prev;

    /**
     * Constructs a new node that stores the provided element.
     *
     * @param element the element to store in this node (may be {@code null} if list allows nulls)
     */
    public MyDLLNode(E element) {
        this.element = element;
        this.next = null;
        this.prev = null;
    }

    /**
     * Returns the element stored in this node.
     *
     * @return the element stored in the node (may be {@code null})
     */
    public E getElement() { return element; }

    /**
     * Replaces the element stored in this node with a new value.
     *
     * @param element the new element to store (may be {@code null})
     */
    public void setElement(E element) { this.element = element; }

    /**
     * Returns the next node in the list.
     *
     * @return the node that follows this node, or {@code null} if this is the tail
     */
    public MyDLLNode<E> getNext() { return next; }

    /**
     * Updates the {@code next} reference for this node.
     *
     * @param next the node that should follow this node (may be {@code null})
     */
    public void setNext(MyDLLNode<E> next) { this.next = next; }

    /**
     * Returns the previous node in the list.
     *
     * @return the node that precedes this node, or {@code null} if this is the head
     */
    public MyDLLNode<E> getPrev() { return prev; }

    /**
     * Updates the {@code prev} reference for this node.
     *
     * @param prev the node that should precede this node (may be {@code null})
     */
    public void setPrev(MyDLLNode<E> prev) { this.prev = prev; }
}
