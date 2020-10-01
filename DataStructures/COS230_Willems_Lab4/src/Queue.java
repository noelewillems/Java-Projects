// queue of Java int as a linked list
public class Queue {
	// Front and rear Node variables
	private Node front;
	private Node rear;
	
	// Inner class Node
	private class Node {
		// Next Node; data variables
		Node next;
		int data;
		
		// Constructors that initialize
		Node(Node next, int data) {
			this.next = null;
			this.data = data;
		}
		
		Node(int data) {
			this.data = data;
		}
	}

	// Constructor that initializes Queue class info
	public Queue() {
		this.front = null;
		this.rear = null;
	}

	// Method that adds newNode(num)
	public void add(int num) {
		Node newNode = new Node(num);
		if(this.isEmpty()) {
			front = newNode;
		}
		// Making newNode the new rear
		newNode.next = rear;
		rear = newNode;	
	}

	// Checks if queue is empty
	public boolean isEmpty() {
		if(front == null) {
			return true;
		} else
			return false;
	}

	// Removes Nodes; if the queue is empty, throws an exception
	public int remove() throws QueueEmptyException {
		int returnData = front.data;
		Node current = rear;
		while(current != null) {
			if(rear == front) {
				rear = null;
				front = null;
				return returnData;
			}
			if(current.next == front) {
				front = current;
				front.next = null;
			}
			current = current.next;
		}
		return returnData;
	}
}
