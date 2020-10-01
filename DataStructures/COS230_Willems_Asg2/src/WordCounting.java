
// Code created by Noel Willems for COS 230 Assignment 2.
// Sources: Caleb Hummel helped me brainstorm on how I would be able to "see" Nodes using an id or something of the like.
// Other sources include javadocs and a regex tester.

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.Scanner;

public class WordCounting<K, V> {

	K key;
	V value;

	// Main method
	// ONLY does these things:
	// Makes sure correct number of args
	// Creates new WordCounting object then runs it
	public static void main(String[] args) {

		try {
			String fileName = args[0];
			WordCounting<String, Integer> wordCount = new WordCounting<String, Integer>();

			if ((args.length != 1)) {
				System.out.println("Incorrect # of args");
				System.exit(0);
			}

			// Method to run the program
			wordCount.run(fileName);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Please enter 1 file.");
		}

	}

	// run method with fileName as input from main
	public void run(String fileName) {
		// Variables:
		// File
		File file = null;
		file = new File(fileName);
		// Scanner
		Scanner sc;
		// Total words in file
		int totalWords = 0;
		// Variable to use for size of hashTable
		BigInteger totalWordsPrime;

		// This try/catch is only for finding how big the hash table should be
		try {
			sc = new Scanner(file);
			sc.useDelimiter("[\\s,.?!]+");

			while (sc.hasNext()) {
				sc.next();
				totalWords++;
			}
		} catch (FileNotFoundException e) {
			System.out.println("FILE NOT FOUND");
			System.out.println(e.getMessage());
		}

		// Size of hashtable is going to be the estimated size * 2 as a prime
		// Used math functions and BigInteger to easily get the next prime of size * 2
		totalWordsPrime = BigInteger.valueOf(totalWords * 2);
		totalWordsPrime = totalWordsPrime.nextProbablePrime();
		Table<String, Integer> hashTable = new HashTable<String, Integer>(totalWordsPrime.intValue());

		try {
			sc = new Scanner(file);
			// Regex for delimiter
			sc.useDelimiter("[\\s,.?!]+");
			String word;
			int value;
			while (sc.hasNext()) {
				word = sc.next();
				word = word.toLowerCase();
				// Regex that simplifies the words
				word = word.replaceAll("[^A-Za-z'A-Za-z]+", "");
				// IF the key does NOT already exist, make a new entry
				if (!word.isEmpty()) {
					if (hashTable.get(word) == null) {
						hashTable.put(word, 1);
					} else {
						value = hashTable.get(word);
						value++;
						hashTable.put(word, value);
					}
				}
			}

			// FOR PRINTING
			System.out.println("...................................................");
			System.out.println("RAW HASHTABLE");
			Iterator<String> iterator = hashTable.iterator();
			String key;
			int valuePrint;
			int index = 0;
			int hashTableSize = 0;

			while (iterator.hasNext()) {
				key = iterator.next();
				valuePrint = hashTable.get(key);
				System.out.println(key + " " + valuePrint);
				hashTableSize++;
			}
			System.out.println("Total distinct words: " + hashTableSize);
			System.out.println("...................................................");

			// Putting stuff into the unorderedArray
			Iterator<String> iterator2 = hashTable.iterator();
			String unorderedArray[] = new String[hashTableSize];
			while (iterator2.hasNext()) {
				key = iterator2.next();
				valuePrint = hashTable.get(key);
				unorderedArray[index] = (key);
				index++;
			}

			System.out.println("PRINTED ORDERED ARRAY");
			System.out.println("...................................................");

			// Calling quickSort to create an ordered array
			String[] array = quickSort(unorderedArray, 0, unorderedArray.length - 1);
			for (int i = 0; i < array.length; i++) {
				System.out.println(array[i]);
			}
		} catch (FileNotFoundException e) {
			System.out.println("FILE NOT FOUND");
			System.out.println(e.getMessage());
		}
	}

	// I did a quicksort to sort my Strings.
	// Sorry if my comments are a confusing explanation!
	public String[] quickSort(String[] array, int lowIndex, int highIndex) {
		// "Partition" that separates the array into chunks
		int partitionIndex;
		// If the lower index is less than the higher index (aka, the partition still
		// has stuff in it)
		if (lowIndex < highIndex) {
			// Partition index
			partitionIndex = partition(array, lowIndex, highIndex);
			// Recursive call
			quickSort(array, lowIndex, partitionIndex - 1);
			quickSort(array, partitionIndex + 1, highIndex);
		}
		// Once everything is done, return the sorted array
		return array;
	}

	// Computes the partition
	private int partition(String[] array, int lowIndex, int highIndex) {
		// Pivot position is going to be set to the highest index; or the last index
		int pivotPosition = highIndex;
		int i = lowIndex - 1;

		// For the length of the partition
		for (int k = lowIndex; k < highIndex; k++) {
			// Comparing array elements using Comparable's .compareTo
			// If the lefthand side is less than the righthand side, returns a negative
			// number
			if (array[k].compareTo(array[pivotPosition]) <= 0) {
				i++;
				// Switching around the positions of stuff in the partition
				String temporary = array[i];
				array[i] = array[k];
				array[k] = temporary;
			}
		}

		// Switching around stuff in the partition
		String temporary = array[i + 1];
		array[i + 1] = array[highIndex];
		array[highIndex] = temporary;

		return i + 1;
	}
}
