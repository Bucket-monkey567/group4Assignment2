package implementations;

import utilities.Iterator;
import utilities.ListADT;

import java.util.NoSuchElementException;
import java.util.Arrays;

/**
 * A simplified dynamic array list implementation similar to Java's ArrayList.
 * @author Alex Raagas
 *
 * @param <E> type of elements stored in the list
 */
public class MyArrayList<E> implements ListADT<E> {

    private E[] data;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Constructs an empty list with default initial capacity.
     */
    @SuppressWarnings("unchecked")
    public MyArrayList() {
        data = (E[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    /**
     * Appends an element to the end of the list.
     *
     * @param toAdd the element to add
     * @return true when successfully added
     * @throws NullPointerException if toAdd is null
     */
    public boolean add(E toAdd) {
        if (toAdd == null)
            throw new NullPointerException("Cannot add null element");
        ensureCapacity(size + 1);
        data[size++] = toAdd;
        return true;
    }

    /**
     * Inserts an element at the specified index.
     *
     * @param index position to insert
     * @param toAdd element to insert
     * @return true when successfully added
     */
    public boolean add(int index, E toAdd) {
        if (toAdd == null)
            throw new NullPointerException("Cannot add null element");
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index: " + index);

        ensureCapacity(size + 1);
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = toAdd;
        size++;
        return true;
    }

    /**
     * Adds all elements from another list.
     *
     * @param toAdd list of elements to add
     * @return true when successful
     * @throws NullPointerException if toAdd is null
     */
    public boolean addAll(ListADT<? extends E> toAdd) {
        if (toAdd == null) throw new NullPointerException();
        for (int i = 0; i < toAdd.size(); i++) {
            add(toAdd.get(i));
        }
        return true;
    }

    /**
     * Gets the element at a specified index.
     *
     * @param index index of the element
     * @return the element
     */
    public E get(int index) {
        checkIndex(index);
        return data[index];
    }

    /**
     * Removes and returns the element at a given index.
     *
     * @param index position to remove
     * @return removed element
     */
    public E remove(int index) {
        checkIndex(index);
        E removed = data[index];
        System.arraycopy(data, index + 1, data, index, size - index - 1);
        data[--size] = null;
        return removed;
    }

    /**
     * Removes the first occurrence of an element.
     *
     * @param toRemove element to remove
     * @return removed element or null if not found
     */
    public E remove(E toRemove) {
        if (toRemove == null) throw new NullPointerException();
        for (int i = 0; i < size; i++) {
            if (toRemove.equals(data[i])) {
                return remove(i);
            }
        }
        return null;
    }

    /**
     * Replaces an element at a given index.
     *
     * @param index the index to replace
     * @param toChange the new element
     * @return the old element
     */
    public E set(int index, E toChange) {
        if (toChange == null) throw new NullPointerException();
        checkIndex(index);
        E old = data[index];
        data[index] = toChange;
        return old;
    }

    /**
     * @return the number of stored elements
     */
    public int size() {
        return size;
    }

    /**
     * Removes all elements from the list.
     */
    public void clear() {
        Arrays.fill(data, 0, size, null);
        size = 0;
    }

    /**
     * @return true if this list has no elements
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Checks whether the list contains a given element.
     *
     * @param toFind the element to locate
     * @return true if found
     */
    public boolean contains(E toFind) {
        if (toFind == null) throw new NullPointerException();
        for (int i = 0; i < size; i++) {
            if (toFind.equals(data[i])) return true;
        }
        return false;
    }

    /**
     * Returns an array containing all elements.
     *
     * @return array copy of elements
     */
    public Object[] toArray() {
        return Arrays.copyOf(data, size);
    }

    /**
     * Stores elements into the given array.
     *
     * @param holder the array to fill
     * @return the filled array
     */
    @SuppressWarnings("unchecked")
    public E[] toArray(E[] holder) {
        if (holder == null) throw new NullPointerException();
        if (holder.length < size) {
            return (E[]) Arrays.copyOf(data, size, holder.getClass());
        }
        System.arraycopy(data, 0, holder, 0, size);
        if (holder.length > size) holder[size] = null;
        return holder;
    }

    /**
     * Returns an iterator that traverses the list from index 0 upward.
     *
     * @return an iterator
     */
    public Iterator<E> iterator() {
        return new ArrayListIterator();
    }

    // ------------------ Helper Methods ------------------

    private void ensureCapacity(int minCapacity) {
        if (minCapacity > data.length) {
            int newCapacity = data.length * 2;
            if (newCapacity < minCapacity)
                newCapacity = minCapacity;
            data = Arrays.copyOf(data, newCapacity);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index);
    }

    /**
     * Iterator implementation for MyArrayList (0 → size-1).
     */
    private class ArrayListIterator implements Iterator<E> {
        private int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            return data[cursor++];
        }
    }
}
