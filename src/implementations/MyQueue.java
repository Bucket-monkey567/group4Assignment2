package implementations;
/**
 * @author Mrinal Jha
 * Queue Handler
 *
 * @param <E> type of nodes stored in the list
 */
import exceptions.EmptyQueueException;
import utilities.Iterator;
import utilities.QueueADT;

public class MyQueue<e> implements QueueADT<e> {
	
	/**
     * Creates a private class where it creates nodes for data to be stored in
     */
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
    
    /**
     * Creates a new queue which has the same data as another queue (copy constructor)
     * 
     * @param the pre-extablished queue
     * @throw can't copy queue if queue DNE
     */
    public MyQueue(QueueADT<e> other) throws NullPointerException {
        if (other == null)
            throw new NullPointerException("Cannot copy from a null queue");


        this.front = null;
        this.rear = null;
        this.size = 0;


        Iterator<e> it = other.iterator();
        while (it.hasNext()) {
            this.enqueue(it.next());
        }
    }
    
    /**
     * Creates a new empty queue
     */
    public MyQueue() {
        this.front = null;
        this.rear = null;
        this.size = 0;    	
    }
    
    /**
     * Places a node at the end of the queue and increases the size of the queue if the queue is empty it makes a new node
     * 
     * @param toAdd, the data that is being added
     * @throw Can't enqueue if ToAdd is null
     */
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
	
	/**
	 * Removes the node at the front of the queue
	 * 
	 * @throw Can't remive is queue is empty
	 * @return The removed Node
	 */
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
	
	/**
	 * Shows a 'peek' of the node
	 * 
	 * @throw cannot show the front node if queue is empty
	 * @return The Front Node 
	 */
	@Override
	public e peek() throws EmptyQueueException {
		if (isEmpty())
			throw new EmptyQueueException("Queue is empty");
		
		return front.data;
	}
	
	/**
	 * Removes all nodes and decreases size to zero
	 */
	@Override
	public void dequeueAll() {
		front = null;
		rear = null;
		size = 0;
		
	}

	/**
	 * Checks whether the queue is empty
	 * 
	 * @return True if isEmpty false if not
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Checks whether the queue has the desired data
	 * 
	 * @param toFind: the data that is being found
	 * @throw NullPointerException Cannot find desired if ToFind is Empty
	 * @return True if it exists False if it dosn't 
	 */
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

	/**
	 * Checks where the desired data is
	 * 
	 * @param toFind: the data that is being found
	 * @return -1 if the data DNE, int that matches the placement of the node in queue if it dose exist
	 */
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

	/**
	 * Returns an iterator over the elements in this queue in proper sequence.
	 * 
	 * @return an iterator over the elements in this queue in proper sequence.
	 */
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

	/**
	 * Checks whether 2 Queues match in data
	 * 
	 * @param that, the other queue
	 * @return False if they are not in same size or if they do not have the same data
	 */
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
	
	/**
	 * Turns the queue into an array
	 * 
	 * @return The array that has the data of the queue
	 */
	@Override
	public Object[] toArray() {
		Object[] arr = new Object[size];
		
		int i = 0;
		for(Node current = front; current != null; current = current.next, i++) {
			arr[i] = current.data;
		}
		
		return arr;
 	}

	/**
	 * converts queue into array but the queue be put into the inputed array
	 * 
	 * @param holder: the inputted array
	 * @throw NullPointerExpection: if holder is null
	 */
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

	/**
	 * Checks if queue is full
	 * 
	 * @return false DLL queues can never be full
	 */
	@Override
	public boolean isFull() {
		return false;
	}

	/**
	 * returns the size of the queue
	 * 
	 * @return the size of the queue
	 */
	@Override
	public int size() {
		return size;
	}

}
