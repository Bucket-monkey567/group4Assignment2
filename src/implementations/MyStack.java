package implementations;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;
import utilities.Iterator;
import utilities.StackADT;

/**
 * MyStack implementation using MyArrayList as the underlying structure.
 * Fixes top-to-bottom order for toArray() and iterator() to match stack semantics.
 *
 * @param <E> type of elements stored in the stack
 */
public class MyStack<E> implements StackADT<E> {

    private MyArrayList<E> list;

    public MyStack() {
        list = new MyArrayList<>();
    }

    @Override
    public void push(E toAdd) throws NullPointerException {
        if (toAdd == null)
            throw new NullPointerException("Cannot push null onto stack.");
        list.add(toAdd);
    }

    @Override
    public E pop() throws EmptyStackException {
        if (isEmpty())
            throw new EmptyStackException();
        return list.remove(list.size() - 1);
    }

    @Override
    public E peek() throws EmptyStackException {
        if (isEmpty())
            throw new EmptyStackException();
        return list.get(list.size() - 1);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(list.size() - 1 - i); // reverse order for stack
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E[] toArray(E[] holder) throws NullPointerException {
        if (holder == null)
            throw new NullPointerException("Array holder cannot be null.");

        int n = list.size();
        E[] result;
        if (holder.length >= n) result = holder;
        else result = (E[]) java.lang.reflect.Array.newInstance(holder.getClass().getComponentType(), n);

        for (int i = 0; i < n; i++) {
            result[i] = list.get(n - 1 - i);
        }

        if (result.length > n) result[n] = null; // per Collection.toArray(E[]) contract

        return result;
    }

    @Override
    public boolean contains(E toFind) throws NullPointerException {
        if (toFind == null)
            throw new NullPointerException();
        return list.contains(toFind);
    }

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

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int current = list.size() - 1; // start from top

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

    @Override
    public boolean equals(StackADT<E> that) {
        if (that == null || this.size() != that.size())
            return false;

        Iterator<E> it1 = this.iterator();
        Iterator<E> it2 = that.iterator();

        while (it1.hasNext() && it2.hasNext()) {
            E a = it1.next();
            E b = it2.next();
            if (!((a == null && b == null) || (a != null && a.equals(b))))
                return false;
        }

        return true;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean stackOverflow() {
        return false; // dynamic stack, no fixed size
    }
}
