// Code created by Noel Willems for COS 230 Assignment 2.
// Sources: Caleb Hummel helped me brainstorm on how I would be able to "see" Nodes using an id or something of the like.
// Other sources include javadocs and a regex tester.

import java.util.Iterator;

// HashTable class implements Table class; uses generics for key/value
public class HashTable<K, V> implements Table<K, V> {
	// Creating an array of LinkedLists 
	private LinkedList<K, V>[] array;

	// Setters and getters
	public void setArray(LinkedList<K, V>[] array) {
		this.array = array;
	}

	public LinkedList<K, V>[] getArray() {
		return array;
	}

	public int getArrayLength() {
		return array.length;
	}

	// Method that returns a boolean if LL is empty
	public boolean LLisEmpty(int currentSlot) {
		if (array[currentSlot].head == null) {
			return true;
		} else {
			return false;
		}
	}

	// Method that returns a boolean if specified Node does not exist
	public boolean doesNodeExist(int arraySlot, int nodeID) {
		return array[arraySlot].doesNodeExist(nodeID);
	}

	// Constructor for hashtable
	public HashTable(int size) {
		array = new LinkedList[size];
		for (int i = 0; i < array.length; i++) {
			array[i] = new LinkedList<K, V>();
		}
	}

	// Method that returns the LL
	public LinkedList<K, V> getLL(int index) {
		LinkedList<K, V> ll = this.array[index];
		return ll;
	}

	// Method that returns the key of a specific node
	public K getKey(int arraySlot, int nodeID) {
		return array[arraySlot].returnKey(nodeID);
	}

	// Method that overrides Table method put
	@Override
	public void put(K key, V value) {
		int hashCode = Math.abs(key.hashCode()) % array.length;
		array[hashCode].add(key, value);
	}

	// Method that overrides Table method get that returns value
	@Override
	public V get(K key) {
		int hashCode = Math.abs(key.hashCode()) % array.length;
		if (array[hashCode].head == null) {
			return null;
		} else {
			// loop through the linked list at that hashCode
			// find the linked list Node whose value is equivalent to the key we are given
			return array[hashCode].findValue(key);
		}
	}

	// Iterator creation of hashIterator
	@Override
	public Iterator<K> iterator() {
		HashTableIterator<K> hashIterator = new HashTableIterator<K>(this);
		return hashIterator;
	}

	// Linked List class
	private class LinkedList<T, S> {
		Node head;
		Node tail;

		LinkedList() {
			head = null;
			tail = null;
		}

		// LinkedList method for adding Nodes
		public void add(T key, S value) {
			Node newNode = new Node(key, value);
			if (head == null) {
				head = newNode;
				tail = newNode;
				head.id = 1;
			} else if (ifKeyExists(key, value)){
			} else {
				newNode.id = tail.id + 1;
				tail.next = newNode;
				tail = newNode;
			}
		}

		// Method for finding values, given a specific key
		public S findValue(T key) {
			Node current = head;
			while (current != null) {
				if (current.key.equals(key)) {
					return current.value;
				}
				current = current.next;
			}
			return null;
		}

		// Method that returns key based on nodeID
		public T returnKey(int nodeID) {
			Node currentNode = head;
			T key = null;

			while (currentNode != null) {
				if (currentNode.id == nodeID) {
					return key = currentNode.key;
				} else {
					currentNode = currentNode.next;
				}
			}
			return key;
		}

		// Returns a boolean if the key exists based on key-value
		public boolean ifKeyExists(T key, S value) {
			Node current = head;
			while (current != null) {
				if (current.key.equals(key)) {
					current.value = value;
					return true;
				}
				current = current.next;
			}
			return false;
		}

		// Node class
		private class Node {
			Node next;
			T key;
			S value;
			int id;
			
			// Node constructor
			Node(T key, S value) {
				next = null;
				this.key = key;
				this.value = value;
			}
		}

		// Method that returns a boolean if the node exists, based on given nodeID
		public boolean doesNodeExist(int nodeID) {
			boolean nodeExists = false;
			Node current = head;
			while (current != null) {
				if (current.id == nodeID) {
					return nodeExists = true;
				}
				current = current.next;
			}
			return nodeExists;
		}
	}
}
