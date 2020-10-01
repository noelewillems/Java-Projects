/*
 * Author: John Hunt
 * This class is a binary heap implemented as a tree
 * it provides a method to build the tree, print the 
 * tree and to remove the last child from the tree.
 * The primary operations of remove and insert are left
 * as exercises
 */

public class Heap {

	class Node {
		int num;
		Node left;
		Node right;

		Node(int num, Node left, Node right) {
			this.num = num;
			this.left = left;
			this.right = right;
		}

		public Node() {
			// TODO Auto-generated constructor stub
		}

		public String toString() {
			return num + "";
		}
	}

	Node root;
	int nodeCnt;

	Heap() {
		root = null;
		nodeCnt = 0;
	}

	/*
	 * Builds a complete binary tree from the array passed in. This method does not
	 * attempt to order the nodes on value. For the resulting tree to have the
	 * quality of heapness the array must be in a correct order.
	 */
	void build(int nums[]) {
		Node nodes[] = new Node[nums.length];
		for (int i = 0; i < nums.length; i++) {
			nodes[i] = new Node(nums[i], null, null);
		}
		root = nodes[0];
		for (int i = 0; i < nums.length / 2; i++) {
			nodes[i].left = nodes[2 * i + 1];
			nodes[i].right = nodes[2 * i + 2];
		}
		nodeCnt = nums.length;
	}

	// Method created by Noel Willems for COS 230 Lab 7
	int remove() {
		// setting current node value
		Node currentRoot = this.root;
		// root value to return
		int rootValue = this.root.num;
		// setting the root to the last child
		this.root.num = this.removeLastChild();

		// Making sure it's not the last number
		if(!(currentRoot.right == null && currentRoot.left == null)) {
			// If it's either the last "subtree" or it needs "rebalancing"
			while ((currentRoot.right == null)
					|| (currentRoot.num < currentRoot.left.num || currentRoot.num < currentRoot.right.num)) {
				boolean biggerLeft = false;
				boolean biggerRight = false;
			
				if (currentRoot.right == null) {
					// if left is bigger (because right wont exist)
					if (currentRoot.num < currentRoot.left.num) {
						int rootTemp = currentRoot.num;
						int leftTemp = currentRoot.left.num;
						currentRoot.left.num = rootTemp;
						currentRoot.num = leftTemp;
						currentRoot = currentRoot.left;
						if (currentRoot.left == null && currentRoot.right == null) {
							break;
						}
					}
					break;
				}
				// setting booleans
				if (currentRoot.left.num > currentRoot.right.num) {
					biggerLeft = true;
				} else {
					biggerRight = true;
				}

				// if left is bigger
				if (biggerLeft) {
					int rootTemp = currentRoot.num;
					int leftTemp = currentRoot.left.num;
					currentRoot.left.num = rootTemp;
					currentRoot.num = leftTemp;
					currentRoot = currentRoot.left;
					if (currentRoot.left == null && currentRoot.right == null) {
						break;
					}
				// if right is bigger
				} else if (biggerRight) {
					int rootTemp = currentRoot.num;
					int rightTemp = currentRoot.right.num;
					currentRoot.right.num = rootTemp;
					currentRoot.num = rightTemp;
					currentRoot = currentRoot.right;
					if (currentRoot.left == null && currentRoot.right == null) {
						break;
					}
				}
			}
		} 
		return rootValue;
	}

	void print(Node c, String indent) {
		if (c != null) {
			System.out.println(indent + c.num);
			print(c.left, indent + "    ");
			print(c.right, indent + "    ");
		}
	}

	/*
	 * Performs a pre-order traversal of the tree printing the data value
	 */
	void printHeap() {
		print(root, "");
	}

	/*
	 * Returns the number of nodes in the tree
	 */
	int size() {
		return nodeCnt;
	}

	/*
	 * Removes the last child in the tree returning the last child's value The
	 * nodeCnt is decremented
	 */
	int removeLastChild() {
		int cnt = nodeCnt;
		String path = "";
		while (cnt >= 1) {
			path = (cnt % 2) + path;
			cnt = cnt / 2;
		}

		int value = -1;
		Node c = root;
		for (int i = 1; i < path.length() - 1; i++) {
			if (path.charAt(i) == '0') {
				c = c.left;
			} else {
				c = c.right;
			}
		}
		if (path.length() == 1) {
			value = root.num;
			root = null;
		} else if (path.charAt(path.length() - 1) == '0') {
			value = c.left.num;
			c.left = null;
		} else {
			value = c.right.num;
			c.right = null;
		}
		nodeCnt--;
		return value;
	}

	public static void main(String[] args) {
		Heap h = new Heap();
		int numbers[] = { 50, 30, 20, 25, 15, 7, 18, 22, 13, 14, 9, 2, 3, 1, 17 };
		h.build(numbers);
		h.printHeap();

		while (h.size() > 0) {
			System.out.println("print: " + h.remove());
			//h.printHeap();
		}
	}

}
