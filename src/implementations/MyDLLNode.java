package implementations;

/**
 * Node used by {@link MyDLL}. Stores an element and links to previous and next nodes.
 *
 * @param <E> element type
 */
public class MyDLLNode<E> {
    private E element;
    private MyDLLNode<E> next;
    private MyDLLNode<E> prev;

    /**
     * Construct a node holding the given element.
     *
     * @param element value to store (may be null if your list allows it)
     */
    public MyDLLNode(E element) {
        this.element = element;
        this.next = null;
        this.prev = null;
    }

    /** @return the stored element */
    public E getElement() { return element; }

    /** @param element replace the stored element */
    public void setElement(E element) { this.element = element; }

    /** @return next node or null */
    public MyDLLNode<E> getNext() { return next; }

    /** @param next set next node */
    public void setNext(MyDLLNode<E> next) { this.next = next; }

    /** @return previous node or null */
    public MyDLLNode<E> getPrev() { return prev; }

    /** @param prev set previous node */
    public void setPrev(MyDLLNode<E> prev) { this.prev = prev; }
}
