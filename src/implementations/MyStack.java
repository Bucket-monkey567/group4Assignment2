package implementations;

import java.util.EmptyStackException;

import exceptions.StackUnderflowException;
import utilities.Iterator;
import utilities.StackADT;

/**
 * @author Alex Raagas
 * MyStack implementation that uses MyArrayList as the underlying
 * data structure. This version uses the custom StackUnderflowException
 * provided in your project instead of Java's EmptyStackException.
 *
 * @param <E> type of elements stored in stack
 */

public class MyStack<E> implements StackADT<E> {

	@Override
	public void push(E toAdd) throws NullPointerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public E pop() throws EmptyStackException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E peek() throws EmptyStackException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E[] toArray(E[] holder) throws NullPointerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(E toFind) throws NullPointerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int search(E toFind) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(StackADT<E> that) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean stackOverflow() {
		// TODO Auto-generated method stub
		return false;
	}

}
