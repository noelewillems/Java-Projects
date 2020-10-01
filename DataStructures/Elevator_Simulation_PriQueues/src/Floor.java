/*
Written by Noel Willems for COS230 Assignment 1: Elevator Simulator
Floor object that contains a priority queue of Passengers.
Only methods are class constructor and getters/setters.
 */
public class Floor {
	PriQue<Passenger> floorQueue;
	int floorNum;

	public Floor() {
		floorQueue = new PriorityQue<Passenger>();
		floorNum = 0;
	}
	
	public Floor(int floorNum) {
		floorQueue = new PriorityQue<Passenger>();
		this.floorNum = floorNum;
	}
	
	public void boardAll(PriQue<Passenger> pq) {
		floorQueue = pq;
	}
}
