// Code written by Noel Willems for COS 230 Lab 5
public class BSTree {
	
	// root = head
	private Node root;

	// Main
	public static void main(String[] args) {
		// bst is a binary search tree
		BSTree bst = new BSTree();
		int count = 0;
		System.out.println("Pre-Order: ");
		bst.preOrder(bst.root, count);
		System.out.println("\nIn-Order: ");
		bst.inOrder(bst.root, count);
		System.out.println("\nPost-Order: ");
		bst.postOrder(bst.root, count);
	}

	// Root, left, right
	public void preOrder(Node root, int count) {

		int c = count;
		Node current = root;

		// If it reaches a "leaf" - aka, tries to call itself on null
		if (current == null) {
			return;
		}
		for (int i = 0; i < c; i++) {
			System.out.print(".");
		}
		System.out.println(current.data);
		preOrder(current.left, c + 3);
		preOrder(current.right, c + 3);
	}

	// Left, root, right
	public void inOrder(Node root, int count) {
		Node current = root;
		int c = count;
		if (current == null) {
			return;
		}
		inOrder(current.left, c + 3);
		for (int i = 0; i < c; i++) {
			System.out.print(".");
		}
		System.out.println(current.data);
		inOrder(current.right, c + 3);
	}

	// Left, right, root
	public void postOrder(Node root, int count) {
		int c = count;
		Node current = root;
		if (current == null) {
			return;
		}

		postOrder(current.left, c + 3);
		postOrder(current.right, c + 3);
		for (int i = 0; i < c; i++) {
			System.out.print(".");
		}
		System.out.println(current.data);

	}

	// Array of ints that will be our starting values
	static int startValues[] = { 50, 20, 45, 55, 75, 12, 97, 17, 83, 76, 34, 66, 27, 53, 48 };

	// Node class
	private class Node {
		// Data
		int data;
		// Left and right nodes of each nodes
		Node left;
		Node right;

		// Constructor
		Node(int data) {
			this.data = data;
			left = right = null;
		}
	}

	// BSTree constructor
	// Initializes the values of the binary search tree to the startValues array
	public BSTree() {
		for (int i = 0; i < startValues.length; i++) {
			insert(startValues[i]);
		}
	}

	// Inserts data in its appropriate place
	public void insert(int data) {
		// assumes no duplicates

		// If the BST is empty, set the root to the new Node values
		if (root == null) {
			root = new Node(data);

			// If the BST is NOT empty
		} else {
			// Begin at head
			Node current = root;
			// Boolean?
			while (true) {
				// If the new Node's data is less than the current Node's data, go left
				if (data < current.data) {
					// If there is nothing to the left of the current Node's data...
					if (current.left == null) {
						// Set current's left to the new Node
						current.left = new Node(data);
						// Escape while loop
						break;
						// If there IS something to the left of the current Node's data...
					} else {
						// Set current to the left Node
						current = current.left;
					}
					// Else, if the new Node's data is GREATER than the current Node's data..
				} else {
					// go right
					// If there is nothing to the right of the current Node
					if (current.right == null) {
						// Set current's right to the new Node
						current.right = new Node(data);
						break;
						// If there is something to the right of the current Node
					} else {
						current = current.right;
					}
				}
			}
		}
	}

}
