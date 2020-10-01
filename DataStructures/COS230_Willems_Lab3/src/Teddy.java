// Teddy.java program
// Code written by Noel Willems for COS 230 Lab 3

public class Teddy {

	public static void main(String[] args) {
		int numBears;
		
		try {
			numBears = Integer.parseInt(args[0]);
			boolean win = picnicTime(numBears);
			if (win) {
				System.out.println("Possible to win!");
			} else {
				System.out.println("Not possible to win!");
			}
			
		} catch (NumberFormatException e) {
			System.out.println("Please enter an integer.");
		}
	}

	// Recursive method
	public static boolean picnicTime(int num) {
		// Return boolean
		boolean canWin = false;
		// Number of input bears
		int numBears = num;

		// If we reach the goal
		if (numBears == 42) {
			return canWin = true;
		}

		// If we don't reach the goal
		if (numBears < 42) {
			return canWin = false;
		}
		
		// If numBears is equal and we haven't won yet, calls itself on smaller input
		if ((numBears % 2) == 0) {
			if (!canWin) {
				canWin = picnicTime(numBears / 2);
			}
		}

		// If numBears is div by 3 or 4 and we haven't won yet, calls itself on smaller input
		if ((numBears % 4 == 0) || (numBears % 3 == 0)) {
			if (!canWin) {
				canWin = picnicTime((numBears) - ((numBears % 10) * ((numBears % 100) / 10)));
			}
		}

		// If numBears is div by 5 we haven't won yet, calls itself on smaller input
		if ((numBears % 5) == 0) {
			if (!canWin) {
				canWin = picnicTime(numBears - 42);
			}
		}
		
		// Returns the evaluation for each recursive call 
		return canWin;
	}

}
