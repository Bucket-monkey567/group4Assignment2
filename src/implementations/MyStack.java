package implementations;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;
import utilities.Iterator;
import utilities.StackADT;

/**
 * A stack implementation backed by a dynamic array-based list (MyArrayList).
 * <p>
 * This stack follows LIFO (Last-In, First-Out) behavior where new elements
 * are pushed to the top and popped from the top. Internally, the top of the
 * stack corresponds to the end of the underlying MyArrayList.
 * </p>
 * @author Alex Raagas
 *
 * @param <E> the type of elements stored in the stack
 */
public class MyStack<E> implements StackADT<E> {

    /** The underlying dynamic list structure. */
    private MyArrayList<E> list;

    /**
     * Constructs an empty stack.
     */
    public MyStack() {
        list = new MyArrayList<>();
    }

    /**
     * Pushes an element onto the top of the stack.
     *
     * @param toAdd the element to push
     * @throws NullPointerException if the element is null
     */
    @Override
    public void push(E toAdd) throws NullPointerException {
        if (toAdd == null)
            throw new NullPointerException("Cannot push null onto stack.");
        list.add(toAdd);
    }

    /**
     * Removes and returns the element at the top of the stack.
     *
     * @return the popped element
     * @throws EmptyStackException if the stack is empty
     */
    @Override
    public E pop() throws EmptyStackException {
        if (isEmpty())
            throw new EmptyStackException();
        return list.remove(list.size() - 1);
    }

    /**
     * Returns the element at the top of the stack without removing it.
     *
     * @return the top element
     * @throws EmptyStackException if the stack is empty
     */
    @Override
    public E peek() throws EmptyStackException {
        if (isEmpty())
            throw new EmptyStackException();
        return list.get(list.size() - 1);
    }

    /**
     * Removes all elements from the stack.
     */
    @Override
    public void clear() {
        list.clear();
    }

    /**
     * Checks whether the stack is empty.
     *
     * @return true if the stack contains no elements
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Returns an array containing all stack elements from top to bottom.
     *
     * @return an array representation of the stack
     */
    @Override
    public Object[] toArray() {
        Object[] result = new Object[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(list.size() - 1 - i);
        }
        return result;
    }

    /**
     * Fills the provided array with stack elements from top to bottom.
     *
     * @param holder the array into which elements are stored
     * @return the filled array
     * @throws NullPointerException if the provided array is null
     */
    @Override
    @SuppressWarnings("unchecked")
    public E[] toArray(E[] holder) throws NullPointerException {
        if (holder == null)
            throw new NullPointerException("Array holder cannot be null.");

        int n = list.size();
        E[] result;

        if (holder.length >= n)
            result = holder;
        else
            result = (E[]) java.lang.reflect.Array.newInstance(
                    holder.getClass().getComponentType(), n);

        for (int i = 0; i < n; i++) {
            result[i] = list.get(n - 1 - i);
        }

        if (result.length > n)
            result[n] = null;

        return result;
    }

    /**
     * Checks whether the stack contains a given element.
     *
     * @param toFind the element to locate
     * @return true if the element exists in the stack
     * @throws NullPointerException if toFind is null
     */
    @Override
    public boolean contains(E toFind) throws NullPointerException {
        if (toFind == null)
            throw new NullPointerException();
        return list.contains(toFind);
    }

    /**
     * Searches for an element and returns its 1-based position from the top.
     *
     * @param toFind the element to search for
     * @return position from the top, or -1 if not found
     */
    @Override
    public int search(E toFind) {
        int pos = 1;
        for (int i = list.size() - 1; i >= 0; i--, pos++) {
            if ((toFind == null && list.get(i) == null) ||
                (toFind != null && toFind.equals(list.get(i)))) {
                return pos;
            }
        }
        return -1;
    }

    /**
     * Returns an iterator that traverses the stack from top to bottom.
     *
     * @return a stack iterator
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int current = list.size() - 1;

            @Override
            public boolean hasNext() {
                return current >= 0;
            }

            @Override
            public E next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                return list.get(current--);
            }
        };
    }

    /**
     * Compares this stack to another stack for equality.
     *
     * @param that another stack
     * @return true if both stacks contain identical elements (top→bottom)
     */
    @Override
    public boolean equals(StackADT<E> that) {
        if (that == null || this.size() != that.size())
            return false;

        Iterator<E> it1 = this.iterator();
        Iterator<E> it2 = that.iterator();

        while (it1.hasNext() && it2.hasNext()) {
            E a = it1.next();
            E b = it2.next();
            if (!((a == null && b == null) ||
                  (a != null && a.equals(b))))
                return false;
        }
        return true;
    }

    /**
     * Returns the number of elements in the stack.
     *
     * @return the stack size
     */
    @Override
    public int size() {
        return list.size();
    }

    /**
     * Indicates whether the stack has overflowed.
     * <p>
     * This implementation is dynamically sized and cannot overflow.
     * </p>
     *
     * @return false always
     */
    @Override
    public boolean stackOverflow() {
        return false;
    }
}
