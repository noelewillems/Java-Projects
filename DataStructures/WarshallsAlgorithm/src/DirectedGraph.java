// Code created by Noel Willems for COS 230 Lab 9
// Used Lafore as a reference

public class DirectedGraph {
	// vertexList contains all of the city names and their corresponding indexes
	private Vertex vertexList[];
	// adjMatrix is an adjacency matrix
	private int adjMatrix[][];
	// Initializing size of adjacency matrix
	private int length = 10;

	// Vertex class; data = city name
	private class Vertex {
		private String cityName = null;

		public Vertex(String cityName) {
			this.cityName = cityName;
		}
	}

	// Constructor
	// Initializes matrix slots to 0
	public DirectedGraph() {
		vertexList = new Vertex[length];
		adjMatrix = new int[length][length];
		for (int j = 0; j < vertexList.length; j++) {
			for (int k = 0; k < vertexList.length; k++) {
				adjMatrix[j][k] = 0;
			}
		}
	}

	// Adds relations from/to cities
	void addRelation(String from, String to) {
		boolean fromExists = false;
		boolean toExists = false;
		int fromIndex = 0;
		int toIndex = 0;

		// Checks if the from/to city already exists
		for (int i = 0; i < vertexList.length; i++) {
			if (vertexList[i] != null && vertexList[i].cityName.equals(from)) {
				fromExists = true;
				fromIndex = i;
			}
			if (vertexList[i] != null && vertexList[i].cityName.equals(to)) {
				toExists = true;
				toIndex = i;
			}
		}

		// If they don't already exist - create them
		for (int i = 0; i < vertexList.length; i++) {
			if (vertexList[i] == null) {
				if (!fromExists) {
					vertexList[i] = new Vertex(from);
					fromExists = true;
					fromIndex = i;
				} else if (!toExists) {
					vertexList[i] = new Vertex(to);
					toExists = true;
					toIndex = i;
				} else {
					break;
				}
			}
		}
		adjMatrix[fromIndex][toIndex] = 1;
	}

	// Prints the relations
	void printAdjMatrix() {
		for (int i = 0; i < length; i++) {
			if (vertexList[i] != null) {
				System.out.println("Relations from " + vertexList[i].cityName + ": ");
			}
			for (int j = 0; j < length; j++) {
				if (adjMatrix[i][j] != 0) {
					System.out.println(" ----> " + vertexList[j].cityName);
				}
			}
		}
		System.out.println("===================================================");
	}

	// Uses Warshall's Algorithm to calculate transitive relations between cities
	void calcWarshall() {
		// Outer loop: rows
		// Inner loop: individual cells
		// Inner-inner loop: activated IF cell = 1.
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				if (adjMatrix[i][j] == 1) {
					for (int x = 0; x < length; x++) {
						if (adjMatrix[x][i] == 1) {
							adjMatrix[x][j] = 1;
						}
					}
				}
			}
		}
	}

	public static void main(String args[]) {
		DirectedGraph g = new DirectedGraph();
		g.addRelation("Atlanta", "Chattanooga");
		g.addRelation("Chattanooga", "Nashville");
		g.addRelation("Chattanooga", "Knoxville");
		g.addRelation("Atlanta", "Birmingham");
		g.addRelation("Atlanta", "Columbia");
		g.addRelation("Columbia", "Charleston");
		g.addRelation("Columbia", "Greenville");
		g.addRelation("Greenville", "Atlanta");
		g.addRelation("Charleston", "Savanna");
		g.addRelation("Savanna", "Atlanta");
		g.addRelation("Savanna", "Jacksonville");
		g.addRelation("Jacksonville", "Atlanta");
		g.addRelation("Knoxville", "Greenville");

		g.printAdjMatrix();
		g.calcWarshall();
		g.printAdjMatrix();
	}
}
