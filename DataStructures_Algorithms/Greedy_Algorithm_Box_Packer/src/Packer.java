// Code written by Noel Willems for COS230 Assignment 3.
// Includes an array heap that resizes itself by increments of 5.
// This program creates the most economic packing list for different boxes and books.

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Packer {
	// Heap of all of the books
	private ParallelArrayHeap<Book> bookHeap;

	public Packer() {
		bookHeap = new ParallelArrayHeap<Book>(1000);
	}

	private class Box {
		String boxType;
		int costPerOz;
		int availableWeight;
		int maxWeight;
		Book[] books;
		int numBooks = 0;
		int totalCost;

		public Box(String bt, int w, int c) {
			boxType = bt;
			maxWeight = w;
			totalCost = c;
			costPerOz = c / maxWeight;
			availableWeight = maxWeight;
			books = new Book[50];
			for (int i = 0; i < books.length; i++) {
				if (books[i] != null) {
					numBooks++;
				}
			}
		}
	}

	private class Book {
		String isbn;
		String bookTitle;
		int weight;

		public Book(String i, String bt, Integer w) {
			isbn = i;
			bookTitle = bt;
			weight = w;
		}

		public String toString() {
			String print = "";
			print = isbn + " | " + bookTitle + " | " + weight;
			return print;
		}
	}

	public Box[] sortBoxTypes(Box[] b) {
		for (int i = 0; i < b.length - 1; i++) {
			for (int j = 0; j < b.length - i - 1; j++) {
				if (b[j] != null && b[j + 1] != null) {
					if (b[j].costPerOz > b[j + 1].costPerOz) {
						Box tempBox = b[j];
						b[j] = b[j + 1];
						b[j + 1] = tempBox;
					}
				}
			}
		}
		return b;
	}

	public static void main(String[] args) {
		Packer packer = new Packer();
		try {
			String shippingOptions = args[0];
			String bookOrders = args[1];

			if ((args.length != 2)) {
				System.out.println("Incorrect number of args (2 required).");
				System.exit(0);
			}
			// Method to run the program
			packer.run(shippingOptions, bookOrders);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Please enter 2 files (shipping options and book orders).");
		}
	}

	private void run(String shippingOptions, String bookOrders) {
		File shippingFile = new File(shippingOptions);
		File booksFile = new File(bookOrders);
		String[] nextLine;
		// This array of Boxes is solely for figuring out the cheapest Box.
		Box[] boxTypes = new Box[30];
		Box box;
		Book book;
		Scanner sc;
		Box[] packedBoxes = new Box[30];
		Book currentBook;

		// Shipping File
		try {
			sc = new Scanner(shippingFile);
			int cnt = 0;
			while (sc.hasNextLine()) {
				nextLine = sc.nextLine().split("/");
				// size/weight in oz/cents
				box = new Box(nextLine[0], Integer.valueOf(nextLine[1]), Integer.valueOf(nextLine[2]));
				boxTypes[cnt] = box;
				cnt++;
			}

		} catch (FileNotFoundException e) {
			System.out.println("FILE NOT FOUND");
			System.out.println(e.getMessage());
		}

		// Book Order File
		// File processing and adding books to the heap
		try {
			sc = new Scanner(booksFile);
			int weight = 0;
			while (sc.hasNextLine()) {
				nextLine = sc.nextLine().split("/");
				// isbn/title/weight
				book = new Book(nextLine[0], nextLine[1], Integer.valueOf(nextLine[2]));
				weight = weight + Integer.valueOf(nextLine[2]);
				bookHeap.insert(Integer.valueOf(nextLine[2]), book);
			}
		} catch (FileNotFoundException e) {
			System.out.println("FILE NOT FOUND");
			System.out.println(e.getMessage());
		}

		// Figure out which is the cheapest box
		Box[] sortedBoxTypes = sortBoxTypes(boxTypes);
		Box cheapestBox = sortedBoxTypes[0];

		while (!bookHeap.isEmpty()) {
			currentBook = bookHeap.remove();
			// Looping through array of packed boxes
			for (int i = 0; i < packedBoxes.length; i++) { 
				if (packedBoxes[i] != null) { // If you find a packed box 
					// If there is available weight in the box, add the book in.
					if (packedBoxes[i].availableWeight >= currentBook.weight) {
						packedBoxes[i].books[packedBoxes[i].numBooks] = currentBook;
						packedBoxes[i].availableWeight = packedBoxes[i].availableWeight - currentBook.weight;
						packedBoxes[i].numBooks++;
						break;
					}
				} else {
					// If there is no available weight in the previous boxes, create a new box and add the book in.
					// Figure out what the cheapest Box type is
					for (int k = 0; k < sortedBoxTypes.length; k++) {
						if (sortedBoxTypes[k] != null) {
							if (sortedBoxTypes[k].maxWeight >= currentBook.weight) {
								cheapestBox = sortedBoxTypes[k];
								break;
							}
						}
					}
					packedBoxes[i] = new Box(cheapestBox.boxType, cheapestBox.maxWeight, cheapestBox.totalCost);
					packedBoxes[i].books[packedBoxes[i].numBooks] = currentBook;
					packedBoxes[i].availableWeight = packedBoxes[i].availableWeight - currentBook.weight;
					packedBoxes[i].numBooks++;
					break;
				}
			}
		}

		// Now, "shrink" larger boxes to smaller boxes if they are able to and the smaller is more economic.
		// First, find empty boxes.
		Book[] temp = new Book[50];
		for (int i = 0; i < packedBoxes.length; i++) {
			if (packedBoxes[i] != null) {
				if (packedBoxes[i].availableWeight > 0) { // If the box is not at max capacity
					// Now, see if there are any smaller size boxes that are also cheaper.
					for (int j = 0; j < sortedBoxTypes.length; j++) {
						// If the sorted box is smaller and the cost is cheaper
						if (sortedBoxTypes[j] != null) {
							if (sortedBoxTypes[j].maxWeight < packedBoxes[i].maxWeight && sortedBoxTypes[j].totalCost < packedBoxes[i].totalCost && packedBoxes[i].maxWeight - packedBoxes[i].availableWeight <= sortedBoxTypes[j].maxWeight) {
								temp = packedBoxes[i].books;
								int tempWeight = packedBoxes[i].maxWeight - packedBoxes[i].availableWeight;
								packedBoxes[i] = new Box(sortedBoxTypes[j].boxType, sortedBoxTypes[j].maxWeight, sortedBoxTypes[j].totalCost);
								packedBoxes[i].books = temp;
								packedBoxes[i].availableWeight = packedBoxes[i].availableWeight - tempWeight;
							}
						}
					}
				}
			}
		}

		// Finally, print out the packing list.
		System.out.println("P A C K I N G   L I S T");
		int totalWeight = 0;
		int totalSpace = 0;
		double totalCost = 0;
		int numBoxes = 0;
		
		// Individual boxes/stats
		for (int i = 0; i < packedBoxes.length; i++) {
			if (packedBoxes[i] != null) {
				System.out.println("\nBox " + (i + 1) + " (" + packedBoxes[i].boxType + ")");
				for (int j = 0; j < packedBoxes[i].books.length; j++) {
					if (packedBoxes[i].books[j] != null) {
						System.out.println(packedBoxes[i].books[j].toString());
					}
				}
				System.out.println("\nBox weight: " + (packedBoxes[i].maxWeight - packedBoxes[i].availableWeight));
				totalWeight = totalWeight + (packedBoxes[i].maxWeight - packedBoxes[i].availableWeight);
				System.out.println("Unused space: " + packedBoxes[i].availableWeight);
				totalSpace = totalSpace + packedBoxes[i].availableWeight;
				System.out.println("Box cost: $" + ((double)packedBoxes[i].totalCost / 100)+"\n");
				totalCost = totalCost + (double)packedBoxes[i].totalCost / 100;
				System.out.println("==========================================================");
			}
		}
		
		// Number of boxes stats
		for(int i = 0; i < packedBoxes.length; i++) {
			if(packedBoxes[i] != null) {
				numBoxes++;
			}
		}
		
		// Print total stats for the whole packing list
		System.out.println("Total weight: " + totalWeight);
		System.out.println("Total unused space: " + totalSpace);
		System.out.println("Total cost: $" + totalCost);
		// Types of boxes stats
		String typeTemp = "";
		for(int i = 0; i < boxTypes.length; i++) {
			int cnt = 0;
			if(boxTypes[i] != null) {
				typeTemp = boxTypes[i].boxType;
				for(int j = 0; j < packedBoxes.length; j++) {
					if(packedBoxes[j] != null && packedBoxes[j].boxType.equals(typeTemp)) {
						cnt++;
					}
				}
				System.out.println("Total number of " + typeTemp + " boxes: " + cnt);
			}
		}
		System.out.println("Total number of boxes: " + numBoxes);
	}
}
