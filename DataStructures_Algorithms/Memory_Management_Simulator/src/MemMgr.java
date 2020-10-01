// Noel Willems: MemMgr
// This class implements the MMInterface and all of its methods. It is a linked list.
// Performs init(), malloc(), and free().

class MemMgr implements MMInterface {

	private Node head;

	// Constructor for MemMgr that initializes head.
	public MemMgr() {
		head = null;
	}

	// Class Node that contains all data for the nodes in the linked list (MemMgr).
	public class Node {
		private Node next;
		private Node prev;
		private int max;
		private int min;
		private boolean free;
		private int id;
		private int size;

		// Constructor for Node that initializes all variables to null or 0.
		public Node() {
			next = null;
			max = 0;
			min = 0;
			free = true;
			id = 0;
			size = 0;
		}

		// Constructor for Node that sets size to s (whatever is passed in).
		public Node(int s) {
			size = s;
		}

		// Constructor for Node that sets size and id to s and i (whatever is passed
		// in).
		public Node(int i, int s) {
			id = i;
			size = s;
		}
	}

	// Method that creates a Node called newNode of specified size.
	// It sets its status as free space and initializes the elements min and max.
	public void init(int size) {
		Node newNode = new Node(size);
		head = newNode;
		newNode.free = true;
		newNode.min = 0;
		newNode.max = size - 1;
	}

	// Method that creates a Node of specified size and adds it to the front of the
	// ll.
	// Checks to make sure the current Node's size is less than the new Node's size.
	// Makes it head if it's in the beginning; otherwise, adds it where it belongs.
	// Sets its status to free = false because it has been allocated.
	public void malloc(int id, int size) throws MyOutOfMemoryException {
		Node newNode = new Node(id, size);
		Node current = head;

		while (current != null) {
			if (current.size > newNode.size && current.free) {
				if (current.prev == null) {
					head = newNode;
				} else {
					current.prev.next = newNode;
				}

				// Placing it in the correct location.
				newNode.prev = current.prev;
				current.prev = newNode;
				newNode.next = current;

				// Setting the min, max, and free to correct values.
				newNode.min = current.min;
				newNode.max = current.min + size - 1;
				newNode.free = false;
				newNode.next.min = newNode.max + 1;
				// Readjusts the size of the current Node - shifting around "memory."
				current.size = current.size - newNode.size;
				break;
			}

			// If the new Node's size is the same size as the current Node's size,
			// and the current node is free, set its status to false.
			if (current.size == newNode.size && current.free) {
				current.free = false;
				current.id = id;
				break;
			}
			// Continuing through the list.
			current = current.next;
		}

		// If there is no room for the specified node, throw exception.
		if (current == null) {
			throw new MyOutOfMemoryException();
		}
	}

	// Method that "frees" a Node of specified id.
	// boolean idDoesNotExist is true when we can't find the specified id.
	public void free(int id) throws MyInvalidMemoryException {
		Node current = head;
		Node current2 = head;
		boolean idDoesNotExist = true;

		// Searching ll - if it finds Node of specified id, set it to free.
		// Boolean set to false because we have found the id.
		while (current != null) {
			if (current.id == id) {
				current.free = true;
				current.id = 0;
				idDoesNotExist = false;
			}
			// Moving through ll
			current = current.next;
		}

		// If it is true that the ID does not exist, throw exception.
		if (idDoesNotExist) {
			throw new MyInvalidMemoryException();
		}

		// Re-setting the max/min/size values of the nodes when one is freed.
		while (current2.next != null) {
			if (current2.free && current2.next.free) {
				current2.max = current2.max + (current2.next.size);
				current2.size = current2.size + current2.next.size;
				current2.next = current2.next.next;
				if (current2.next != null) {
					current2.next.prev = current2;
				} else {
					break;
				}
			}

			if (!current2.next.free || !current2.free) {
				current2 = current2.next;
			}
		}
	}

	// Printing out the linked list.
	public void print() {
		Node curr;
		curr = head;
		while (curr != null) {
			if (curr.free) {
				System.out.println("Free space: [" + curr.min + "-" + curr.max + "]");
			} else if (!curr.free) {
				System.out.println("Allocated space: ID " + curr.id + " [" + curr.min + "-" + curr.max + "]");
			}
			curr = curr.next;
		}
	}
}