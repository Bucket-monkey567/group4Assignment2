package implementations;

import exceptions.EmptyQueueException;
import utilities.Iterator;
import utilities.QueueADT;

public class MyQueue<e> implements QueueADT<e> {

	@Override
	public void enqueue(e toAdd) throws NullPointerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public e dequeue() throws EmptyQueueException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public e peek() throws EmptyQueueException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dequeueAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(e toFind) throws NullPointerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int search(e toFind) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterator<e> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(QueueADT<e> that) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public e[] toArray(e[] holder) throws NullPointerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isFull() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

}
