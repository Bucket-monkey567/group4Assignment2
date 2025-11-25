package implementations;

import utilities.Iterator;
import utilities.ListADT;

import java.util.NoSuchElementException;

/**
 * Doubly-linked list implementation that implements {@link utilities.ListADT}.
 * This class does NOT use java.util collection classes for node storage (assignment requirement).
 *
 * @param <E> element type
 */
public class MyDLL<E> implements ListADT<E> {

    private MyDLLNode<E> head;
    private MyDLLNode<E> tail;
    private int size;

    /** Construct an empty list. */
    public MyDLL() {
        head = null;
        tail = null;
        size = 0;
    }

    // --- ListADT methods -------------------------------------------------

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        // unlink nodes to help GC
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

    @Override
    public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException {
        if (toAdd == null) throw new NullPointerException();
        if (index < 0 || index > size) throw new IndexOutOfBoundsException("Index: " + index);

        if (index == 0) {
            addFirstInternal(toAdd);
        } else if (index == size) {
            addLastInternal(toAdd);
        } else {
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

    @Override
    public boolean add(E toAdd) throws NullPointerException {
        if (toAdd == null) throw new NullPointerException();
        addLastInternal(toAdd);
        return true;
    }

    @Override
    public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException {
        if (toAdd == null) throw new NullPointerException();
        for (int i = 0; i < toAdd.size(); i++) {
            add(toAdd.get(i));
        }
        return true;
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        checkIndex(index);
        return nodeAt(index).getElement();
    }

    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        checkIndex(index);
        MyDLLNode<E> node = nodeAt(index);
        E val = node.getElement();
        unlink(node);
        return val;
    }

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

    @Override
    public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException {
        if (toChange == null) throw new NullPointerException();
        checkIndex(index);
        MyDLLNode<E> node = nodeAt(index);
        E old = node.getElement();
        node.setElement(toChange);
        return old;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

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

    @Override
    public Iterator<E> iterator() {
        return new DLLIterator();
    }

    // --- Additional helpers used by driver/tests -------------------------

    /** Return the head node (driver may inspect nodes). */
    public MyDLLNode<E> getHead() { return head; }

    /** Convenience: add at front */
    public void addFirst(E item) { add(0, item); }

    /** Convenience: add at end */
    public void addLast(E item) { add(item); }

    /** Remove first element or null if empty */
    public E removeFirst() {
        if (head == null) return null;
        return remove(0);
    }

    /** Remove last element or null if empty */
    public E removeLast() {
        if (tail == null) return null;
        return remove(size - 1);
    }

    /** Return tail node */
    public MyDLLNode<E> getTail() { return tail; }

    // -------------------- internal helpers -------------------------------

    /** Return node at index by walking from nearer end */
    private MyDLLNode<E> nodeAt(int index) {
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

    /** Unlink node, update head/tail/size */
    private void unlink(MyDLLNode<E> node) {
        MyDLLNode<E> prev = node.getPrev();
        MyDLLNode<E> next = node.getNext();

        if (prev != null) prev.setNext(next);
        else head = next;

        if (next != null) next.setPrev(prev);
        else tail = prev;

        node.setNext(null);
        node.setPrev(null);
        node.setElement(null);
        size--;
    }

    private void addLastInternal(E item) {
        MyDLLNode<E> node = new MyDLLNode<>(item);
        if (tail == null) {
            head = tail = node;
        } else {
            tail.setNext(node);
            node.setPrev(tail);
            tail = node;
        }
        size++;
    }

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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index: " + index);
    }

    // ---------------- Iterator implementation ----------------

    private class DLLIterator implements Iterator<E> {
        private MyDLLNode<E> current = head;

        @Override
        public boolean hasNext() { return current != null; }

        @Override
        public E next() {
            if (current == null) throw new NoSuchElementException();
            E val = current.getElement();
            current = current.getNext();
            return val;
        }
    }
}
