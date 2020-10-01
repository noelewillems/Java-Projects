// Suggestion: put code to extract a digit from a number in a separate method in this class
// Implements algorithm
public class Radix {

	// Array of 10 queues, one for each number possible (0-9)
	private Queue[] queuesArray = new Queue[10];

	public Radix() {
		for (int i = 0; i < queuesArray.length; i++) {
			queuesArray[i] = new Queue();
		}
	}

	// Sort method
	public Queue sort(Queue inq) {
		int number;
		try {
			int count = 1;
			boolean done = false;
			while(!done) {
				done = true;
				while (!inq.isEmpty()) {
					number = inq.remove();
					int digit = extractDigit(number, count);
					if(digit != 0) {
						done = false;
					}
					queuesArray[digit].add(number);
				}
				for(int i = 0; i < 10; i++) {
					while(!queuesArray[i].isEmpty()) {
						int temp = queuesArray[i].remove();
						inq.add(temp);
					}
				}
				count++;
			}
		} catch (QueueEmptyException e) {
			System.out.println(e.getMessage());
		}
		return inq;
	}

	// Extract digit method
	// Extracts a digit from a specific place
	public int extractDigit(int number, int place) {
		String numString = Integer.toString(number);
		if (place > numString.length()) {
			return 0;
		}

		char digit = numString.charAt(numString.length() - place);
		return Character.getNumericValue(digit);
	}
}
