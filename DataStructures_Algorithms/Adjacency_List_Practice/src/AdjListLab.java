// Noel Willems
// COS 230 Lab 8

public class AdjListLab {

	// AdjList = LinkedList
	private class AdjList {
		// Head
		private Edge head;
		// City name label
		private String cityName;


		// Constructor
		public AdjList(String from) {
			cityName = from;
		}
	}

	// Edge = node
	private class Edge {
		// Index of the city
		int index;
		// Next "node"
		Edge next;

		// Constructor
		public Edge(Edge next, int index) {
			this.next = next;
			this.index = index;
		}
	}

	// Array of adjacency lists; size = 50
	AdjList[] vertex = new AdjList[50];

	// This method adds relations between cities.
	void addRelation(String from, String to) {
		// Booleans to track if the origin/destination cities exist
		boolean fromExists = false;
		boolean toExists = false;
		// Integers to establish indices of the origin/destination cities
		int fromIndex = 0;
		int toIndex = 0;

		// If they already exist
		for (int i = 0; i < vertex.length; i++) {
			if (vertex[i] != null && vertex[i].cityName.equals(from)) {
				fromExists = true;
				fromIndex = i;
			}
			if (vertex[i] != null && vertex[i].cityName.equals(to)) {
				toExists = true;
				toIndex = i;
			}
		}

		// If they don't already exist - create them
		for (int i = 0; i < vertex.length; i++) {
			if (vertex[i] == null) {
				if (!fromExists) {
					vertex[i] = new AdjList(from);
					fromExists = true;
					fromIndex = i;
				}

				else if (!toExists) {
					vertex[i] = new AdjList(to);
					toExists = true;
					toIndex = i;
				}

				else {
					break;
				}
			}
		}
		// Creating the links between the from/to cities
		vertex[fromIndex].head = new Edge(vertex[fromIndex].head, toIndex);
		vertex[toIndex].head = new Edge(vertex[toIndex].head, fromIndex);
	}

	// Prints the adjacency lists in each index of the vertex array
	void printAdjList() {
		for (int i = 0; i < vertex.length; i++) {
			if (vertex[i] != null) {
				System.out.println("\nRelations from " + vertex[i].cityName + ": ");
				Edge current = vertex[i].head;
				while (current != null) {
					if (current == vertex[i].head) {
						System.out.print(vertex[current.index].cityName);
					} else {
						System.out.print(", ");
						System.out.print(vertex[current.index].cityName);
					}
					current = current.next;
				}
				System.out.println();
			}
		}
	}

	public static void main(String args[]) {
		AdjListLab g = new AdjListLab();
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
		g.addRelation("Greenville", "Knoxville");

		g.printAdjList();
	}
}
