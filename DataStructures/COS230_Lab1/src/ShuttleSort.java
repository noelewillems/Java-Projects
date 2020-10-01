
/*

Code created by Noel Willems for COS 230 LAB 0
Sources:
For generating random within a range: https://dzone.com/articles/random-number-generation-in-java
General concept: https://en.wikipedia.org/wiki/Cocktail_shaker_sort
===========================================================================================
Answers to questions in lab:
1. O(n^2)
2. How ordered the list of elements already is. The more ordered they are, the quicker the sort.
3. The other sorts we discussed will always take the same amount of time, regardless of whether
there is 3 things to sort or 3000. 

*/
import java.util.Arrays;

public class ShuttleSort {

	public void display(int[] array) {
		System.out.println(Arrays.toString(array));
	}

	public void sort(int[] array) {
		int comparisons = 0;
		int swaps = 0;

		boolean flag;
		do {
			flag = false;
			for (int i = 0; i < array.length - 2; i++) {
				comparisons++;
				if (array[i] > array[i + 1]) {
					swaps++;
					int temp = array[i];
					array[i] = array[i + 1];
					array[i + 1] = temp;
					flag = true;
				}
			}

			if (!flag) {
				break;
			}

			flag = false;
			for (int i = array.length - 2; i > 0; i--) {
				if (array[i] > array[i + 1]) {
					int temp = array[i];
					array[i] = array[i + 1];
					array[i + 1] = temp;
				}
			}
		} while (!flag);
		System.out.println("Swaps: " + swaps);
		System.out.println("Comparisons: " + comparisons);
	}
	

	public static void main(String[] args) {

		ShuttleSort sort = new ShuttleSort();

		// VARIABLES
		int num = Integer.parseInt(args[0]);
		int min = 0;
		int max = 999;
		int[] numArray = new int[num];
		int rand = 0;

		// POPULATING numArray
		for (int i = 0; i < num; i++) {
			rand = (int) (Math.random() * ((max - min) + 1)) + min;
			numArray[i] = rand;
		}

		// PRINT UNSORTED ARRAY
		System.out.println("Unsorted array: ");
		sort.display(numArray);

		// SORT THE ARRAY
		sort.sort(numArray);

		// PRINT SORTED ARRAY
		System.out.println("Sorted array: ");
		sort.display(numArray);


	}

}
