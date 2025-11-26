package implementations;

import utilities.Iterator;
import utilities.ListADT;

import java.util.NoSuchElementException;

/**
 * Doubly-linked list implementation that implements {@link utilities.ListADT}.
 * <p>
 * This implementation uses {@code MyDLLNode<E>} objects linked in both directions
 * (prev/next). It avoids use of Java collection classes for internal storage,
 * which matches the assignment requirement. The list supports all operations
 * required by {@code ListADT} and provides additional convenience methods
 * (e.g., {@code addFirst}, {@code addLast}, {@code removeFirst}, {@code getHead})
 * used by the driver code.
 * </p>
 *
 * @param <E> element type
 */
public class MyDLL<E> implements ListADT<E> {

	/** First node (head) of the list; {@code null} when the list is empty. */
    private MyDLLNode<E> head;
    /** Last node (tail) of the list; {@code null} when the list is empty. */
    private MyDLLNode<E> tail;
    /** Number of elements currently stored in the list. */
    private int size;

    /**
     * Constructs an empty {@code MyDLL}.
     * <p>
     * Postcondition: {@code head == tail == null} and {@code size == 0}.
     * </p>
     */
    public MyDLL() {
        head = null;
        tail = null;
        size = 0;
    }

    // ListADT METHODS
    
    /**
     * Returns the number of elements in this list.
     *
     * @return the size of the list (>= 0)
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes all elements from the list.
     * <p>
     * Implementation detail: this method walks the nodes and severs references
     * to help the garbage collector reclaim node objects quickly.
     * </p>
     * Postcondition: {@code head == tail == null} and {@code size == 0}.
     */
    @Override
    public void clear() {
    	// Walk and unlink nodes to avoid lingering references
        MyDLLNode<E> cur = head;
        while (cur != null) {
            MyDLLNode<E> next = cur.getNext();
            cur.setPrev(null);
            cur.setNext(null);
            cur.setElement(null);
            cur = next;
        }
        head = tail = null;
        size = 0;
    }

    /**
     * Inserts the specified element at the specified position in this list.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right.
     *
     * Preconditions:
     * <ul>
     *   <li>{@code toAdd} must not be {@code null} (throws {@code NullPointerException}).</li>
     *   <li>{@code index} must be in range {@code 0 <= index <= size}.</li>
     * </ul>
     *
     * Postcondition: the new element is inserted and {@code size} increases by 1.
     *
     * @param index position at which to insert (0-based, may equal {@code size} to append)
     * @param toAdd element to insert
     * @return {@code true} if addition succeeded
     * @throws NullPointerException if {@code toAdd} is {@code null}
     * @throws IndexOutOfBoundsException if {@code index} is out of range
     */
    @Override
    public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException {
        if (toAdd == null) throw new NullPointerException();
        if (index < 0 || index > size) throw new IndexOutOfBoundsException("Index: " + index);

     // Fast paths for head and tail insertion
        if (index == 0) {
            addFirstInternal(toAdd);
        } else if (index == size) {
            addLastInternal(toAdd);
        } else {
        	// Insert in the middle: find successor and link new node between predecessor and successor
            MyDLLNode<E> succ = nodeAt(index);
            MyDLLNode<E> pred = succ.getPrev();
            MyDLLNode<E> node = new MyDLLNode<>(toAdd);

            node.setNext(succ);
            node.setPrev(pred);
            pred.setNext(node);
            succ.setPrev(node);
            size++;
        }
        return true;
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param toAdd element to append (must not be {@code null})
     * @return {@code true} if added
     * @throws NullPointerException if {@code toAdd} is {@code null}
     */
    @Override
    public boolean add(E toAdd) throws NullPointerException {
        if (toAdd == null) throw new NullPointerException();
        addLastInternal(toAdd);
        return true;
    }

    /**
     * Appends all elements from the provided {@code ListADT} to the end of this list.
     * Elements are appended in the order returned by {@code toAdd.get(i)}.
     *
     * @param toAdd the source list (must not be {@code null})
     * @return {@code true} once all elements are appended
     * @throws NullPointerException if {@code toAdd} is {@code null}
     */
    @Override
    public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException {
        if (toAdd == null) throw new NullPointerException();
        for (int i = 0; i < toAdd.size(); i++) {
            add(toAdd.get(i));
        }
        return true;
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of element to return (0-based)
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if {@code index} is out of range {@code 0 <= index < size}
     */
    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        checkIndex(index);
        return nodeAt(index).getElement();
    }

    /**
     * Removes the element at the specified position in this list and returns it.
     *
     * @param index index of the element to remove (0-based)
     * @return the removed element
     * @throws IndexOutOfBoundsException if {@code index} is out of range {@code 0 <= index < size}
     */
    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        checkIndex(index);
        MyDLLNode<E> node = nodeAt(index);
        E val = node.getElement();
        unlink(node);
        return val;
    }

    /**
     * Removes the first occurrence of the specified element from this list, if present.
     *
     * @param toRemove the element to remove (must not be {@code null})
     * @return the removed element if found, or {@code null} if not present
     * @throws NullPointerException if {@code toRemove} is {@code null}
     */
    @Override
    public E remove(E toRemove) throws NullPointerException {
        if (toRemove == null) throw new NullPointerException();
        MyDLLNode<E> cur = head;
        while (cur != null) {
            if (toRemove.equals(cur.getElement())) {
                E val = cur.getElement();
                unlink(cur);
                return val;
            }
            cur = cur.getNext();
        }
        return null;
    }

    /**
     * Replaces the element at the specified position with the given element.
     *
     * @param index index of the element to replace (0-based)
     * @param toChange the new element (must not be {@code null})
     * @return the previous element at the specified position
     * @throws NullPointerException if {@code toChange} is {@code null}
     * @throws IndexOutOfBoundsException if {@code index} is out of range
     */
    @Override
    public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException {
        if (toChange == null) throw new NullPointerException();
        checkIndex(index);
        MyDLLNode<E> node = nodeAt(index);
        E old = node.getElement();
        node.setElement(toChange);
        return old;
    }

    /**
     * Returns {@code true} if the list contains no elements.
     *
     * @return {@code true} if empty; {@code false} otherwise
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns {@code true} if the list contains the specified element.
     *
     * @param toFind element to search for (must not be {@code null})
     * @return {@code true} if element is found; {@code false} otherwise
     * @throws NullPointerException if {@code toFind} is {@code null}
     */
    @Override
    public boolean contains(E toFind) throws NullPointerException {
        if (toFind == null) throw new NullPointerException();
        MyDLLNode<E> cur = head;
        while (cur != null) {
            if (toFind.equals(cur.getElement())) return true;
            cur = cur.getNext();
        }
        return false;
    }

    /**
     * Returns an array containing all elements in proper sequence (head -> tail).
     *
     * @return an {@code Object[]} of length {@code size} containing list elements
     */
    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size];
        int i = 0;
        MyDLLNode<E> cur = head;
        while (cur != null) {
            arr[i++] = cur.getElement();
            cur = cur.getNext();
        }
        return arr;
    }

    /**
     * Returns an array containing all elements in proper sequence; if the provided
     * array {@code toHold} has sufficient length it is filled and returned; otherwise
     * a new array of the same runtime type is allocated and returned.
     *
     * @param toHold the array into which the elements are to be stored (must not be {@code null})
     * @return an array containing the elements of this list
     * @throws NullPointerException if {@code toHold} is {@code null}
     */
    @Override
    @SuppressWarnings("unchecked")
    public E[] toArray(E[] toHold) throws NullPointerException {
        if (toHold == null) throw new NullPointerException();
        if (toHold.length < size) {
            toHold = (E[]) java.lang.reflect.Array.newInstance(toHold.getClass().getComponentType(), size);
        }
        int i = 0;
        MyDLLNode<E> cur = head;
        while (cur != null) {
            toHold[i++] = cur.getElement();
            cur = cur.getNext();
        }
        if (toHold.length > size) toHold[size] = null;
        return toHold;
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence
     * (from head to tail).
     *
     * @return iterator that traverses the list
     */
    @Override
    public Iterator<E> iterator() {
        return new DLLIterator();
    }

    // ADDITIONAL HELPERS

    /**
     * Returns the head node (used by the driver to inspect nodes directly).
     *
     * @return the head node, or {@code null} if list is empty
     */
    public MyDLLNode<E> getHead() { return head; }

    /**
     * Convenience method: insert the specified element at the front of the list.
     *
     * @param item element to add at the head (must not be {@code null})
     * @throws NullPointerException if {@code item} is {@code null}
     */
    public void addFirst(E item) { add(0, item); }

    /**
     * Convenience method: append the specified element at the end of the list.
     *
     * @param item element to add at the tail (must not be {@code null})
     * @throws NullPointerException if {@code item} is {@code null}
     */
    public void addLast(E item) { add(item); }

    /**
     * Removes and returns the first element of the list.
     *
     * @return the removed element, or {@code null} if the list is empty
     */
    public E removeFirst() {
        if (head == null) return null;
        return remove(0);
    }

    /**
     * Removes and returns the last element of the list.
     *
     * @return the removed element, or {@code null} if the list is empty
     */
    public E removeLast() {
        if (tail == null) return null;
        return remove(size - 1);
    }

    /**
     * Returns the tail node (may be {@code null} if empty).
     *
     * @return the tail node
     */
    public MyDLLNode<E> getTail() { return tail; }

    // INTERNAL HELPERS

    /**
     * Returns the node at the specified index by walking from the nearer end
     * (head or tail). This optimizes traversal time.
     *
     * Precondition: index should be in range 0..size-1 (caller is expected to check).
     *
     * @param index index of the desired node (0-based)
     * @return the node at {@code index}
     */
    private MyDLLNode<E> nodeAt(int index) {
    	// Walk from head when index is in the first half; otherwise walk from tail.
        if (index < (size >> 1)) {
            MyDLLNode<E> cur = head;
            for (int i = 0; i < index; i++) cur = cur.getNext();
            return cur;
        } else {
            MyDLLNode<E> cur = tail;
            for (int i = size - 1; i > index; i--) cur = cur.getPrev();
            return cur;
        }
    }

    /**
     * Unlinks (removes) the provided node from the list and updates head/tail/size.
     * The method also severs node references to aid garbage collection.
     *
     * Postcondition: the node is removed and {@code size} is decremented by 1.
     *
     * @param node the node to unlink (must be non-null and currently in the list)
     */
    private void unlink(MyDLLNode<E> node) {
        MyDLLNode<E> prev = node.getPrev();
        MyDLLNode<E> next = node.getNext();

        if (prev != null) prev.setNext(next);
        else head = next; // node was head

        if (next != null) next.setPrev(prev);
        else tail = prev; // node was tail

     // clear removed node's links and element to help GC
        node.setNext(null);
        node.setPrev(null);
        node.setElement(null);
        size--;
    }

    /**
     * Internal helper that appends an item to the end of the list.
     * Used by {@link #add(E)} and {@link #addAll(ListADT)}.
     *
     * @param item element to append (must not be {@code null})
     */
    private void addLastInternal(E item) {
        MyDLLNode<E> node = new MyDLLNode<>(item);
        if (tail == null) {
        	// List was empty: new node becomes both head and tail
            head = tail = node;
        } else {
            tail.setNext(node);
            node.setPrev(tail);
            tail = node;
        }
        size++;
    }

    /**
     * Internal helper that inserts an item at the beginning of the list.
     *
     * @param item element to add at head (must not be {@code null})
     */
    private void addFirstInternal(E item) {
        MyDLLNode<E> node = new MyDLLNode<>(item);
        if (head == null) {
            head = tail = node;
        } else {
            node.setNext(head);
            head.setPrev(node);
            head = node;
        }
        size++;
    }

    /**
     * Checks that the provided index is valid for accessing elements.
     *
     * @param index index to validate
     * @throws IndexOutOfBoundsException if {@code index < 0 || index >= size}
     */
    private void checkIndex(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index: " + index);
    }

    // ITERATOR IMPLEMENTATION

    /**
     * Iterator that traverses the list from head to tail.
     * Uses the custom {@code utilities.Iterator} interface required by the assignment.
     */
    private class DLLIterator implements Iterator<E> {
    	/** Current node returned by next() call; starts at head. */
        private MyDLLNode<E> current = head;

        /**
         * {@inheritDoc}
         *
         * @return {@code true} if there is another element to return
         */
        @Override
        public boolean hasNext() { return current != null; }

        /**
         * {@inheritDoc}
         *
         * @return next element in the iteration
         * @throws NoSuchElementException if no elements remain
         */
        @Override
        public E next() {
            if (current == null) throw new NoSuchElementException();
            E val = current.getElement();
            current = current.getNext();
            return val;
        }
    }
}
