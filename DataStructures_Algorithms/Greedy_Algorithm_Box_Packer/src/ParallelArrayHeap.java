// Used Lafore as a source for the insert and remove methods.
public class ParallelArrayHeap<T> implements PriQue<T> {
	Node<T>[] array;
	private int currentSize;

	private class Node<N> {
		int weight;
		N data;

		public Node(int weight, N data) {
			this.weight = weight;
			this.data = data;
		}

		public int getWeight() {
			return this.weight;
		}
	}

	public ParallelArrayHeap(int arraySize) {
		array = new Node[5];
		currentSize = 0;
	}

	@Override
	public T remove() {
		Node<T> root = array[0];
		array[0] = array[--currentSize];
		int index = 0;
		int largerChild;
		Node<T> top = array[index];
		while (index < currentSize / 2) { 
			int leftChild = 2 * index + 1;
			int rightChild = leftChild + 1;

			// find larger child
			if (rightChild < currentSize && array[leftChild].getWeight() < array[rightChild].getWeight()) {
				largerChild = rightChild;
			} else {
				largerChild = leftChild;
			} 
			
			if (top.getWeight() >= array[largerChild].getWeight()) {
				break;
			}
			array[index] = array[largerChild];
			index = largerChild; 
		}
		array[index] = top;
		return root.data;
	}

	@Override
	public boolean isEmpty() {
		return currentSize == 0;
	}

	@Override
	public void insert(int weight, T data) {
		if (currentSize == array.length) {
			Node<T>[] newArray = new Node[array.length + 5];
			for(int i = 0; i < array.length; i++) {
				if(array[i] != null) {
					newArray[i] = array[i];
				}
			}
			array = newArray;
		}
		Node<T> newNode = new Node<T>(weight, data);
		array[currentSize] = newNode;
		int index = currentSize++;
		int parent = (index - 1) / 2;
		Node<T> bottom = array[index];
		while (index > 0 && array[parent].getWeight() < bottom.getWeight()) {
			array[index] = array[parent]; // move it down
			index = parent;
			parent = (parent - 1) / 2;
		} // end while
		array[index] = bottom;
	}

	@Override
	public void print() {
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null) {
				System.out.println(array[i].getWeight());
			}
		}
	}
}
