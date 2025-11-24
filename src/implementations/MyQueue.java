package implementations;

import exceptions.EmptyQueueException;
import utilities.Iterator;
import utilities.QueueADT;

public class MyQueue<e> implements QueueADT<e> {
	
	//internal class
    private class Node {
        e data;
        Node next;

        Node(e data) {
            this.data = data;
        }
    }
    
    private Node front;
    private Node rear;
    private int size = 0;
    
    // Copy constructor: creates a new queue that is a deep copy of another queue
    public MyQueue(QueueADT<e> other) throws NullPointerException {
        if (other == null)
            throw new NullPointerException("Cannot copy from a null queue");

        // Initialize empty queue
        this.front = null;
        this.rear = null;
        this.size = 0;

        // Copy all elements using the iterator
        Iterator<e> it = other.iterator();
        while (it.hasNext()) {
            this.enqueue(it.next());
        }
    }
    
    public MyQueue() {
        this.front = null;
        this.rear = null;
        this.size = 0;    	
    }

	@Override
	public void enqueue(e toAdd) throws NullPointerException {
		if (toAdd == null)
			throw new NullPointerException("Cannot enqueue null");
		Node newNode = new Node(toAdd); 
		
		if(isEmpty()) {
			front = newNode;
			rear = newNode;
		}
		else {
			rear.next = newNode;
			rear = newNode;
		}
		
		size++;
	}

	@Override
	public e dequeue() throws EmptyQueueException {
		if(isEmpty()) {
			throw new EmptyQueueException("Queue is empty");
		}
		
		e removed = front.data;
		front = front.next;
		
		if (front == null)
			rear = null;
		
		size--;
		return removed;
	}

	@Override
	public e peek() throws EmptyQueueException {
		if (isEmpty())
			throw new EmptyQueueException("Queue is empty");
		
		return front.data;
	}

	@Override
	public void dequeueAll() {
		front = null;
		rear = null;
		size = 0;
		
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean contains(e toFind) throws NullPointerException {
		if(toFind == null)
			throw new NullPointerException("Cannot search for null");
		
		Node current = front;
		while (current!= null) {
			if(current.data.equals(toFind))
				return true;
			current = current.next;
		}
		
		return false;
	}

	@Override
	public int search(e toFind) {
		if (toFind == null)
			return -1;
		
		Node current = front;
		int index = 1;
		
		while (current != null) {
			if (current.data.equals(toFind))
				return index;
			
			current = current.next;
			index++;
		}
		return -1;
		
	}

	@Override
	public Iterator<e> iterator() {
		return new Iterator<e>() {
	        Node current = front;

	        @Override
	        public boolean hasNext() {
	            return current != null;
	        }

	        @Override
	        public e next() {
	        	if(current == null) {
	        		throw new java.util.NoSuchElementException("No more elements in queue");
	        	}
	            e data = current.data;
	            current = current.next;
	            return data;
	        }
	    };
	}

	@Override
	public boolean equals(QueueADT<e> that) {
		if ((that == null) || (this.size != that.size()))
			return false;
		Iterator<e> it1 = this.iterator();
		Iterator<e> it2 = that.iterator();
		
		while (it1.hasNext() && it2.hasNext()) {
			e a = it1.next();
			e b = it2.next();
			
			if(!a.equals(b))
				return false;
		}
		
		return true;
	}

	@Override
	public Object[] toArray() {
		Object[] arr = new Object[size];
		
		int i = 0;
		for(Node current = front; current != null; current = current.next, i++) {
			arr[i] = current.data;
		}
		
		return arr;
 	}

	@Override
	public e[] toArray(e[] holder) throws NullPointerException {
		if(holder == null)
			throw new NullPointerException("Holder array cannot be null");
		
		if (holder.length < size) {
			holder = (e[]) java.lang.reflect.Array.newInstance(
	                holder.getClass().getComponentType(), size);
		}
		int i = 0;
		for (Node current = front; current != null; current = current.next, i++) {
			holder[i] = current.data;
		}
		
		return holder;
	}

	@Override
	public boolean isFull() {
		return false;
	}

	@Override
	public int size() {
		return size;
	}

}
