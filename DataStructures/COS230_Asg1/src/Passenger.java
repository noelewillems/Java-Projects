/*
Written by Noel Willems for COS230 Assignment 1: Elevator Simulator
Passenger object.
Only methods are class constructor and getters/setters.
 */
public class Passenger {

	// Passenger variables
	private int arrivalTime;
	private int destinationFloor;
	private int timeSpent;
	private int id;
	private int whenCanBoardAgain;
	private int totalWaitingTime;
	private boolean hasTravelled;
	
	public Passenger() {
		arrivalTime = 0;
		destinationFloor = 0;
		timeSpent = 0;
		id = 0;
		whenCanBoardAgain = 0;
		totalWaitingTime = 0;
		hasTravelled = false;
	}
	
	public boolean isHasTravelled() {
		return hasTravelled;
	}

	public void setHasTravelled(boolean haveTravelled) {
		this.hasTravelled = haveTravelled;
	}

	public int getTotalWaitingTime() {
		return totalWaitingTime;
	}

	public void setTotalWaitingTime(int totalWaitingTime) {
		this.totalWaitingTime = totalWaitingTime;
	}

	public int getId() {
		return id;
	}
	
	public int getWhenCanBoardAgain() {
		return whenCanBoardAgain;
	}
	
	public int getArrivalTime() {
		return arrivalTime;
	}

	public int getDestinationFloor() {
		return destinationFloor;
	}

	public int getTimeSpent() {
		return timeSpent;
	}

	public void setArrivalTime(int at) {
		arrivalTime = at;
	}
	
	public void setDestinationFloor(int df) {
		destinationFloor = df;
	}
	
	public void setTimeSpent(int ts) {
		timeSpent = ts;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setWhenCanBoardAgain(int wcba) {
		this.whenCanBoardAgain = wcba;
	}
	
	public void print() {
		System.out.println("Passenger " + id + " , arrivalTime " + arrivalTime + ", destFloor " + destinationFloor);
	}
}
