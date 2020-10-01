// Code created by Noel Willems for COS 230 Assignment 2.
// Sources: Caleb Hummel helped me brainstorm on how I would be able to "see" Nodes using an id or something of the like.
// Other sources include javadocs and a regex tester.

import java.util.Iterator;

public class HashTableIterator<K> implements Iterator<K> {

	private HashTable<K, ?> hashTable;
	private Finder finder = new Finder();
	private int iteratorChainLocation;
	private int iteratorSlot;

	// Object within Iterator that goes ahead of it and examines the contents
	private class Finder {
		int chainLocation; // location in LL
		int slot; // location in array
		
		Finder() {
			this.chainLocation = 0;
			this.slot = 0;
		}
	}

	public HashTableIterator(HashTable<K, ?> hashTable) {
		this.hashTable = hashTable;
		this.iteratorChainLocation = 0;
		this.iteratorSlot = 0;
	}

	// Overrides the Iterator class's hasNext()
	@Override
	public boolean hasNext() {
		boolean hasNext = false;
		// Initializing the finder's slot/location to the iterator's slot/location
		// The iterator will follow the finder
		finder.slot = this.iteratorSlot;
		finder.chainLocation = this.iteratorChainLocation;

		while (finder.slot != hashTable.getArrayLength()) {
			if (hashTable.LLisEmpty(finder.slot)) {
				// If the specific array slot is empty, continue
				finder.slot++;
				hasNext = false;
			} else if (hashTable.doesNodeExist(finder.slot, finder.chainLocation + 1)) {
				return hasNext = true;
			} else {
				finder.chainLocation = 0;
				finder.slot++;
			}
		}
		return hasNext;
	}

	// Overrides the Iterator class's next()
	@Override
	public K next() {
		// While we are not at the end of the hashTable
		while (this.iteratorSlot != hashTable.getArrayLength()) {
			if (hashTable.LLisEmpty(this.iteratorSlot)) {
				// If the specific array slot is empty, continue
				this.iteratorSlot++;
			} else if (hashTable.doesNodeExist(this.iteratorSlot, this.iteratorChainLocation + 1)) {
				this.iteratorChainLocation++;
				return hashTable.getKey(this.iteratorSlot, this.iteratorChainLocation);
			} else {
				this.iteratorChainLocation = 0;
				this.iteratorSlot++;
			}
		}
		return null;
	}
}
