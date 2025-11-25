package implementations;

import utilities.Iterator;
import utilities.ListADT;

import java.util.NoSuchElementException;
import java.util.Arrays;

// implemented ListADT to myarray list
public class MyArrayList<E> implements ListADT<E> {
	 private E[] data;
	 private int size;
	 private static final int DEFAULT_CAPACITY = 10;
	 
	 @SuppressWarnings("unchecked")
	    public MyArrayList() {
	        data = (E[]) new Object[DEFAULT_CAPACITY];
	        size = 0;
	    }

	    public boolean add(E toAdd) {
	        if (toAdd == null)
	            throw new NullPointerException("Cannot add null element");
	        ensureCapacity(size + 1);
	        data[size++] = toAdd;
	        return true;
	    }

	    public boolean add(int index, E toAdd) {
	        if (toAdd == null)
	            throw new NullPointerException("Cannot add null element");
	        if (index < 0 || index > size)
	            throw new IndexOutOfBoundsException("Index: " + index);

	        ensureCapacity(size + 1);
	        // shift elements to the right
	        System.arraycopy(data, index, data, index + 1, size - index);
	        data[index] = toAdd;
	        size++;
	        return true;
	    }

	    public boolean addAll(ListADT<? extends E> toAdd) {
	        if (toAdd == null) throw new NullPointerException();
	        for (int i = 0; i < toAdd.size(); i++) {
	            add(toAdd.get(i));
	        }
	        return true;
	    }

	    public E get(int index) {
	        checkIndex(index);
	        return data[index];
	    }

	    public E remove(int index) {
	        checkIndex(index);
	        E removed = data[index];
	        System.arraycopy(data, index + 1, data, index, size - index - 1);
	        data[--size] = null;
	        return removed;
	    }

	    public E remove(E toRemove) {
	        if (toRemove == null) throw new NullPointerException();
	        for (int i = 0; i < size; i++) {
	            if (toRemove.equals(data[i])) {
	                return remove(i);
	            }
	        }
	        return null;
	    }

	    public E set(int index, E toChange) {
	        if (toChange == null) throw new NullPointerException();
	        checkIndex(index);
	        E old = data[index];
	        data[index] = toChange;
	        return old;
	    }

	    public int size() {
	        return size;
	    }

	    public void clear() {
	        Arrays.fill(data, 0, size, null);
	        size = 0;
	    }

	    public boolean isEmpty() {
	        return size == 0;
	    }

	    public boolean contains(E toFind) {
	        if (toFind == null) throw new NullPointerException();
	        for (int i = 0; i < size; i++) {
	            if (toFind.equals(data[i])) return true;
	        }
	        return false;
	    }

	    public Object[] toArray() {
	        return Arrays.copyOf(data, size);
	    }

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

	    public Iterator<E> iterator() {
	        return new ArrayListIterator();
	    }

	    // --- Internal Helpers ---
	    private void ensureCapacity(int minCapacity) {
	        if (minCapacity > data.length) {
	            int newCapacity = data.length * 2;
	            if (newCapacity < minCapacity) newCapacity = minCapacity;
	            data = Arrays.copyOf(data, newCapacity);
	        }
	    }

	    private void checkIndex(int index) {
	        if (index < 0 || index >= size)
	            throw new IndexOutOfBoundsException("Index: " + index);
	    }

	    // --- Inner Iterator Class ---
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
