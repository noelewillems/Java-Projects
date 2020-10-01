// Code written by Noel Willems for COS 230 Lab 5
public class BSTreeArray {
	private int[] array;

	public BSTreeArray() {
		array = new int[100];
	}

	public void insert(int num) {
		int currentIndex = 0;
		while (true) {
			
			if(array[0] == 0) {
				array[0] = num;
				break;
			}
			
			if (num > array[currentIndex]) {
				// go right
				currentIndex = ((2 * currentIndex) + 2);
				if(array[currentIndex] == 0) {
					array[currentIndex] = num;
					break;
				}
			} else {
				// go left
				currentIndex = ((2 * currentIndex) + 1);
				if(array[currentIndex] == 0) {
					array[currentIndex] = num;
					break;
				}
			}
		}
	}

	// ROOT LEFT RIGHT
	public void preorder(int root, int count) {
		// trying to print array[] in preorder style
		// if the current index is 0, then return
		// otherwise, print array at current index
		// then print array at current index = 2 * currentIndex + 1
		// then print array at current index = 2 * currentIndex + 2
		int c = count; 
		if(array[root] == 0) {
			return;
		}
		
		for (int i = 0; i < c; i++) {
			System.out.print(".");
		}
		
		System.out.println(array[root]);
		preorder(2 * root + 1, c + 3);
		preorder(2 * root + 2, c + 3);
	}

	public static void main(String[] args) {
		BSTreeArray bsta = new BSTreeArray();
		// nums we are inserting
		int[] nums = {50, 25, 75, 10, 15, 5, 53, 29, 79, 78, 111, 33};
		for(int i = 0; i < nums.length; i++) {
			bsta.insert(nums[i]);
		}
		bsta.preorder(0, 0);
	}
}
