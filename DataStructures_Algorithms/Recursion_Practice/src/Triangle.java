// Triangle.java program
// Code written by Noel Willems for COS 230 Lab 3

public class Triangle {

	public static void main(String[] args) {
		int m = Integer.parseInt(args[0]); // min num asterisks
		int n = Integer.parseInt(args[1]); // max num asterisks

		recursiveTriangle(m, n);
	}

	// Recursive method
	public static void recursiveTriangle(int m, int n) {
		// As long as min num asterisks is less than max numa sterisks
		if (m <= n) {
			// Print a line of asterisks based on the min up to the max
			for (int i = 0; i < m; i++) {
				System.out.print("*");
			}
			System.out.println();

			// Calling itself on different input
			recursiveTriangle(m + 1, n);

			// Repeating again to print the descending side of triangle
			if (m <= n) {
				for (int i = 0; i < m; i++) {
					System.out.print("*");
				}
				System.out.println();
			}
		}
	}
}
