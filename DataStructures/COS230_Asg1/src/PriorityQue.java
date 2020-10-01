/*
Written by Noel Willems for COS230 Assignment 1: Elevator Simulator
Creates priority queue that implements PriQue<E> Interface of generic type.
Linked List priority queue.
 */

public class PriorityQue<E> implements PriQue<E> {

	private Node head;
	// Nodes to imitiate a linked list.
	private class Node {
		// Variables
		int priority;
		E data;
		Node next;

		Node(int priority, E data) {
			this.priority = priority;
			this.data = data;
		}
	}

	// Insert values method
	public void insert(int priority, E data) {
		Node newNode = new Node(priority, data);
		Node current = head;
		boolean flag = false;

		// NULL
		if (head == null) {
			head = newNode;
			
		} else {			
			while (current != null && flag == false) {		
				// REPLACE HEAD
				if(current.priority > newNode.priority && current == head) {
					newNode.next = head;
					head = newNode;
					flag = true;
				}
				
				// END
				else if(newNode.priority > current.priority && current.next == null) {
					current.next = newNode;
					flag = true;
				}
				
				// IN BETWEEN
				else if (newNode.priority > current.priority && newNode.priority < current.next.priority) {
					newNode.next = current.next;
					current.next = newNode;
					flag = true;
				}
								
				// DUPLICATES
				else if(newNode.priority == current.priority && flag == false) {
					newNode.next = current.next;
					current.next = newNode;
					flag = true;
				}
				current = current.next;
			}
		}
	}
	
	// Remove a Node
	public E remove() {
		E data = head.data;
		head = head.next;
		return data;
	}

	// Peek at Node with highest priority
	public E peek() {
		return head.data;
	}

	// Check if empty
	public boolean isEmpty() {
		if (head == null) {
			return true;
		} else {
			return false;
		}
	}
	
//	public void print() {
//		Node current = head;
//		while(current != null) {
//			System.out.println(current.priority);
//			current = current.next;
//		}
//	}
}
