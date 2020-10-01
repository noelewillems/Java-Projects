/*
Written by Noel Willems for COS230 Assignment 1: Elevator Simulator
 */

// Imports
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// Simulation class 
public class ElevatorSimulator {
	// Variables
	private int numFloors = 0;
	private String fileName;
	private int clock = 0;
	private boolean peopleInBuilding = true;
	// 'building' is an array of Floor priority queues.
	private Floor[] building;
	// 'statistics' is an array of Passengers for computing statistics later on.
	// Passengers are added to 'statistics' when they leave the building.
	private Passenger[] statistics;

	// Constructor for ElevatorSimulator.
	public ElevatorSimulator(int numFloors, String fileName) {
		this.numFloors = numFloors;
		this.fileName = fileName;
	}

	// Method for computing passenger statistics at the end.
	public void computeStats(Passenger[] statistics) {
		int totalAllWaits = 0;
		int avgWait;
		int longestWait = 0;
		int longestWaitID = 0;
		for (int i = 0; i < statistics.length; i++) {
			totalAllWaits = statistics[i].getTotalWaitingTime() + totalAllWaits;
			if (statistics[i].getTotalWaitingTime() > longestWait) {
				longestWait = statistics[i].getTotalWaitingTime();
				longestWaitID = statistics[i].getId();
			}
		}
		avgWait = ((totalAllWaits) / (statistics.length * 2));
		System.out.println("=============STATISTICS=============");
		System.out.println("For " + statistics.length * 2 + " rides, the average wait was " + avgWait + ".");
		System.out.println("The longest waiting time was " + longestWait + " for customer " + longestWaitID + ".");
	}

	// Elevator class - inner class of Elevator Simulator
	private class Elevator {
		// Variables
		// maxOccupants can be easily changed here.
		int maxOccupants = 5;
		// Creating an array of Passenger type that will represent the elevator's
		// passengers.
		Passenger[] elevatorPassengers = new Passenger[maxOccupants];

		// Method that checks if the elevator is at max capacity.
		public boolean isFull() {
			boolean isFull = true;
			for (int i = 0; i < elevatorPassengers.length; i++) {
				if (elevatorPassengers[i] == null) {
					isFull = false;
				}
			}
			return isFull;
		}

		// Method that unboards passengers from the elevator onto their destination
		// floor.
		public void unboard(int currentFloor, Passenger[] elePassengers) {
			// Boolean that checks if people are going to board/unboard so the clock knows
			// when to tick up.
			boolean boarded = false;
			for (int i = 0; i < elePassengers.length; i++) {
				// If the elevator is not empty...
				if (elePassengers[i] != null) {
					// If any of the elevator passengers' destination is the current floor...
					if (elePassengers[i].getDestinationFloor() == currentFloor) {
						// If their destination floor is the ground floor (aka, they're leaving the
						// building)...
						if (elePassengers[i].isHasTravelled() && currentFloor == 0) {
							// Print out the passenger's info, add them to the array of statistics
							System.out.println("At time " + clock + ", passenger " + elePassengers[i].getId()
									+ " unboarded elevator onto floor " + currentFloor);
							for (int k = 0; k < statistics.length; k++) {
								if (statistics[k] == null) {
									statistics[k] = elePassengers[i];
									if (k == statistics.length - 1) {
										peopleInBuilding = false;
									}
									// Remove them from the elevator
									elePassengers[i] = null;
									break;
								}
							}

							// If the passengers' destination floor is NOT the ground floor -
							// AKA, they're NOT leaving the building...
						} else {
							// Print passenger info
							System.out.println("At time " + clock + ", passenger " + elePassengers[i].getId()
									+ " unboarded elevator onto floor " + currentFloor);
							// When the passenger can board the elevator again, based on the current clock
							// time and the time they will spend on their destination floor
							elePassengers[i].setWhenCanBoardAgain(clock + elePassengers[i].getTimeSpent());
							// Set their new destination floor to 0 (ground floor) because they need to
							// leave after they're done
							elePassengers[i].setDestinationFloor(0);
							// Add them to the current floor's queue
							building[currentFloor].floorQueue.insert(elePassengers[i].getArrivalTime(),
									elePassengers[i]);
							// Remove them from the elevator
							elePassengers[i] = null;
						}
					}
				}
			}
			// Clock ticks up.
			if (!boarded) {
				clock++;
				boarded = true;
			}
		}

		// Method that boards passengers onto the elevator.
		public void board(int currentFloor, Passenger[] elePassengers) {
			// Boolean that checks if people are going to board/unboard so the clock knows
			// when to tick up.
			boolean boarded = false;
			// If the current floor is not empty (aka, there are passengers that need to board the elevator)
			if (!building[currentFloor].floorQueue.isEmpty()) {
				// If the current floor is the ground floor...
				if (currentFloor == 0) {
					// While the passengers' arrival time is less than or equal to the clock AND
					// the elevator is not at max capacity
					while (building[currentFloor].floorQueue.peek().getArrivalTime() <= clock && !this.isFull()) {
						for (int j = 0; j < elePassengers.length; j++) {
							// Find an empty slot in the elePassengers array
							if (elePassengers[j] == null) {
								// Remove the passeger from the building floor queue to the elevator
								elePassengers[j] = building[currentFloor].floorQueue.remove();
								// Set their total waiting time to the current clock time because they're still waiting on ground floor.				
								elePassengers[j].setTotalWaitingTime(clock);
								// Set their boolean to true - they have travelled
								elePassengers[j].setHasTravelled(true);
								// Print info
								System.out.println("At time " + clock + ", passenger " + elePassengers[j].getId()
										+ " on floor " + currentFloor + " boarded elevator; destination floor is "
										+ elePassengers[j].getDestinationFloor());
								break;
							}
						}
						// If there is nobody on the current floor queue to board, go to the next floor
						if (building[currentFloor].floorQueue.isEmpty()) {
							break;
						}
					}
					// If the floor is NOT the ground floor
				} else {
					// Basically do the same exact thing as above, except totalWaitingTime is computed differently because they're not on the ground floor anymore.
					while (building[currentFloor].floorQueue.peek().getWhenCanBoardAgain() <= clock && !this.isFull()) {
						for (int j = 0; j < elePassengers.length; j++) {
							if (elePassengers[j] == null) {
								elePassengers[j] = building[currentFloor].floorQueue.remove();
								elePassengers[j].setTotalWaitingTime(clock - elePassengers[j].getWhenCanBoardAgain());
								elePassengers[j].setHasTravelled(true);
								System.out.println("At time " + clock + ", passenger " + elePassengers[j].getId()
										+ " on floor " + currentFloor + " boarded elevator; destination floor is "
										+ elePassengers[j].getDestinationFloor());
								break;
							}
						}
						// If there is nobody on the current floor queue to board, go to the next floor
						if (building[currentFloor].floorQueue.isEmpty()) {
							break;
						}
					}
				}
			}
			// Clock ticks up.
			if (!boarded) {
				clock++;
				boarded = true;
			}
		}
	}

	public static void main(String[] args) {
		// If there are the incorrect number of arguments 
		// AKA, not only number of floors and fileName
		if ((args.length <= 1) || (args.length > 2)){
			System.out.println("Incorrect # of args");
			System.exit(0);
		}

		// Catch some exceptions if the input is wrong.
		try {
			// Creating new elevSim object.
			ElevatorSimulator elevSim = new ElevatorSimulator(Integer.parseInt(args[0]), args[1]);
			// Running elevSim object.
			elevSim.run();
			// Computing stats of elevSim.
			elevSim.computeStats(elevSim.statistics);
		} catch (NumberFormatException e) {
			System.out.println("Please enter an integer for the number of floors.");
			System.out.println(e.toString());
		} catch (FileNotFoundException e) {
			System.out.println("File does not exist!");
			System.out.println(e.toString());
		}

	}

	// Runs the elevSim object.
	public void run() throws FileNotFoundException {
		// File set-up stuff
		File file = null;
		file = new File(fileName);
		Scanner sc = new Scanner(file);
		
		// Variables/initializations
		building = new Floor[numFloors];
		// whileCount is essentially number of passengers.
		int whileCount = 0;
		// Creating a priority queue of all the passengers in the simulator.
		PriQue<Passenger> allPassengers = new PriorityQue<Passenger>();
		String splitLine[];
		
		// While the file is not empty
		while (sc.hasNext()) {
			String line = sc.nextLine();
			// If there's a comment, ignore it.
			if (line.contains("//")) {
				continue;
			} else {
				// Setting up each passenger object
				splitLine = line.split(" ");
				Passenger p = new Passenger();
				p.setArrivalTime(Integer.parseInt(splitLine[0]));
				p.setDestinationFloor(Integer.parseInt(splitLine[1]));
				p.setTimeSpent(Integer.parseInt(splitLine[2]));
				p.setId(whileCount);
				// Inserting passenger into queue
				allPassengers.insert(p.getArrivalTime(), p);
				whileCount++;
			}
		}
		// Passenger array to compute statistics of size all passengers
		statistics = new Passenger[whileCount];

		// Creating the floors in the building array of Floor type
		for (int i = 0; i < numFloors; i++) {
			Floor floor = new Floor(i);
			building[i] = floor;
		}

		// All passengers begin on ground floor
		building[0].boardAll(allPassengers);

		// Begin the elevator
		Elevator elevator = new Elevator();

		// Current floor variable
		int currentFloor;

		// While there eare still people in the building
		while (peopleInBuilding) {
			// For loop goes up the floors
			for (int i = 0; i < numFloors; i++) {
				currentFloor = i;
				elevator.unboard(currentFloor, elevator.elevatorPassengers);
				elevator.board(currentFloor, elevator.elevatorPassengers);
				// Clock ticks
				clock++;
			}

			// For loop goes down the floors
			for (int i = numFloors - 2; i > 0; i--) {
				currentFloor = i;
				elevator.unboard(currentFloor, elevator.elevatorPassengers);
				elevator.board(currentFloor, elevator.elevatorPassengers);
				// Clock ticks
				clock++;
			}
		}
		sc.close();
	}
}
