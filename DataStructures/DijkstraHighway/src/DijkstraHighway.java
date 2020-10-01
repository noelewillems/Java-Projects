
// Code written by Noel Willems for COS 230 Assignment 5. 
// calcDist method written by Dr. John Hunt.
// Sources used for further understanding of Dijkstra's Algorithm:
// https://medium.com/basecs/finding-the-shortest-path-with-a-little-help-from-dijkstra-613149fbdc8e
// Extra credit: handles usai.gra and also passes correct edge/path names to output files.

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class DijkstraHighway {

	AdjList[] vertices;

	public static void main(String[] args) {
		DijkstraHighway dh = new DijkstraHighway();
		// java DijkstraHighway <input file name> <output file name> <start point> <end
		// point>
		// For the start and end point use vertex names
		try {
			if (args.length != 4) {
				System.out.println("Incorrect number of arguments.");
				System.out.println(
						"Format: java DijkstraHighway <input file name> <output file name> <start point> <end point>");
			}
			String inputFile = args[0];
			String outputFile = args[1];
			String start = args[2];
			String end = args[3];

			dh.run(inputFile, outputFile, start, end);

		} catch (Exception e) {
			System.out.println("Exception!");
			System.out.println(e.getMessage());
		}
	}

	double calcDist(double lat1, double lon1, double lat2, double lon2) {
		double R = 6371; // km
		double radLat1 = Math.toRadians(lat1);
		double radLat2 = Math.toRadians(lat2);
		double radDiffLat = Math.toRadians(lat2 - lat1);
		double radDiffLon = Math.toRadians(lon2 - lon1);

		double a = Math.sin(radDiffLat / 2) * Math.sin(radDiffLat / 2)
				+ Math.cos(radLat1) * Math.cos(radLat2) * Math.sin(radDiffLon / 2) * Math.sin(radDiffLon / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return R * c;
	}

	// Each vertex is an adjacency list
	private class AdjList {
		private Edge head;
		// City name label
		private String vertexName;
		// Latitudes and longitudes
		private double latitude;
		private double longitude;
		private double shortestDistFromStart;
		private AdjList prevAdjList;
		private Edge prevEdge;

		public AdjList(String vertName, double lat, double lon) {
			this.vertexName = vertName;
			this.latitude = lat;
			this.longitude = lon;
		}
	}

	private class Edge {
		String roadName;
		double distance;
		int destination;
		Edge next;

		// Constructor
		public Edge(String rn, double di, int de) {
			this.roadName = rn;
			this.distance = di;
			this.destination = de;
		}
	}

	private void run(String inputFile, String outputFile, String start, String end) {
		File input = new File(inputFile);
		File output = new File(outputFile);
		int numVertices = 0;
		int numEdges = 0;
		Scanner sc;
		String header;
		String headerNxtLn[];
		String nextLine;
		int cnt = 0;
		String lat;
		String lon;
		String vertexNxtLn[];
		String vertexName;
		String edgeNxtLn[];

		// Input file
		try {
			sc = new Scanner(input);
			header = sc.nextLine();
			nextLine = sc.nextLine();
			headerNxtLn = header.split(" ");
			numVertices = Integer.valueOf(headerNxtLn[0]);
			numEdges = Integer.valueOf(headerNxtLn[1]);
			int fromIndex;
			int toIndex;
			String roadName;
			double distance;
			Edge edge1;
			Edge edge2;

			// VERTICES
			// ////////////////////////////////////////////////////////////////////////////////
			// The vertices are the indexes of the array, and the edges are the elements in
			// each index.
			vertices = new AdjList[numVertices];
			while (sc.hasNextLine() && !Character.isDigit(nextLine.charAt(0))) {
				vertexNxtLn = nextLine.split(" ");
				vertexName = vertexNxtLn[0].replaceAll(" ", "");
				lat = vertexNxtLn[1];
				lon = vertexNxtLn[2];
				vertices[cnt] = new AdjList(vertexName, Double.parseDouble(lat.replaceAll(",", "")),
						Double.parseDouble(lon));
				nextLine = sc.nextLine();
				cnt++;
			}

			// EDGES ///////////////////////////////////////////////////////
			while (sc.hasNextLine() && Character.isDigit(nextLine.charAt(0))) {
				edgeNxtLn = nextLine.split(" ");
				fromIndex = Integer.parseInt(edgeNxtLn[0]);
				toIndex = Integer.parseInt(edgeNxtLn[1]);
				roadName = edgeNxtLn[2];

				// calculate the distance using Hunt's method
				distance = calcDist(vertices[fromIndex].latitude, vertices[fromIndex].longitude,
						vertices[toIndex].latitude, vertices[toIndex].longitude);
				// create two new Nodes
				edge1 = new Edge(roadName, distance, toIndex);
				edge2 = new Edge(roadName, distance, fromIndex);

				// ADJ LISTS ADDING NODES////////////////////////////////////////
				// First one
				// If the adjacency list at the fromIndex is empty, just add it in
				Edge current = vertices[fromIndex].head;
				;
				if (vertices[fromIndex].head == null) {
					vertices[fromIndex].head = edge1;
				} else {
					while (current.next != null) {
						current = current.next;
					}
					current.next = edge1;
				}

				if (vertices[toIndex].head == null) {
					vertices[toIndex].head = edge2;
				} else {
					current = vertices[toIndex].head;
					while (current.next != null) {
						current = current.next;
					}
					current.next = edge2;
				}
				nextLine = sc.nextLine();
			}

			edgeNxtLn = nextLine.split(" ");
			fromIndex = Integer.parseInt(edgeNxtLn[0]);
			toIndex = Integer.parseInt(edgeNxtLn[1]);
			roadName = edgeNxtLn[2];
			distance = calcDist(vertices[fromIndex].latitude, vertices[fromIndex].longitude, vertices[toIndex].latitude,
					vertices[toIndex].longitude);
			// create two new Nodes
			edge1 = new Edge(roadName, distance, toIndex);
			edge2 = new Edge(roadName, distance, fromIndex);
			Edge current = vertices[fromIndex].head;
			;
			if (vertices[fromIndex].head == null) {
				vertices[fromIndex].head = edge1;
			} else {
				while (current.next != null) {
					current = current.next;
				}
				current.next = edge1;
			}

			if (vertices[toIndex].head == null) {
				vertices[toIndex].head = edge2;
			} else {
				current = vertices[toIndex].head;
				while (current.next != null) {
					current = current.next;
				}
				current.next = edge2;
			}

			boolean startExists = false;
			boolean endExists = false;
			for(int i = 0; i < vertices.length; i++) {
				if(vertices[i] != null) {
					if(vertices[i].vertexName.equals(start)) {
						startExists = true;
					}
					if(vertices[i].vertexName.equals(end)) {
						endExists = true;
					}
				}
			}
			if(!startExists || !endExists) {
				System.err.println("Please enter valid start/ending vertices.");
				System.exit(0);
			}
			dijkstra(start, end, numVertices, outputFile);
		} catch (Exception e) {
			System.out.println("Exception!");
			System.out.println(e);
		}
	}

	public void dijkstra(String start, String end, int numVertices, String outputFile) {
		//////////////////////////////// DIJKSTRA////////////////////////////////////

		AdjList[] visited = new AdjList[numVertices];
		AdjList[] unvisited = new AdjList[numVertices];
		AdjList startVertex = null;
		AdjList endVertex = null;
		AdjList currentVertex = null;

		// Determine the starting and ending vertices
		for (int i = 0; i < vertices.length; i++) {
			if (vertices[i].vertexName.equals(start)) {
				startVertex = vertices[i];
			}
			if (vertices[i].vertexName.equals(end)) {
				endVertex = vertices[i];
			}
		}

		// Initialize the unvisited array to all of the vertices.
		for (int i = 0; i < unvisited.length; i++) {
			unvisited[i] = vertices[i];
			unvisited[i].shortestDistFromStart = -1;
			unvisited[i].prevAdjList = null;
			unvisited[i].prevEdge = null;
		}

		startVertex.shortestDistFromStart = 0;
		currentVertex = startVertex;

		while (currentVertex != endVertex) {
			Edge current = currentVertex.head;
			// Start looping through currentVertex's edges
			while (current != null) {
				// Check is a "temp" variable for each connection to the current vertex
				AdjList check = vertices[current.destination];

				boolean done = false;
				// Check to see if the current connection has been visited
				for (int i = 0; i < visited.length; i++) {
					if (visited[i] != null) {
						if (check.vertexName.equals(visited[i].vertexName)) {
							done = true;
						}
					}
				}

				if (done) {
					current = current.next;
					continue;
				}
				double tempDistance = current.distance + currentVertex.shortestDistFromStart;
				// if we have not seen this vertex
				if (check.shortestDistFromStart == -1) {
					check.prevAdjList = currentVertex;
					check.prevEdge = current;
					check.shortestDistFromStart = tempDistance;
				}

				// If the check adjList's distance from start is shorter than current's current
				// distance from start
				if (check.shortestDistFromStart > current.distance + currentVertex.shortestDistFromStart) {
					// Update the shortest distance from start
					check.shortestDistFromStart = current.distance + currentVertex.shortestDistFromStart;
					check.prevAdjList = currentVertex;
					check.prevEdge = current;
				}
				current = current.next;
			}
			AdjList smallestConnection = null;
			for (int i = 0; i < unvisited.length; i++) {
				if (unvisited[i] != null) {
					if (unvisited[i] == currentVertex) {
						visited[i] = unvisited[i];
						unvisited[i] = null;
					}
				}
			}

			for (int i = 0; i < unvisited.length; i++) {
				if (unvisited[i] != null) {
					if (smallestConnection == null && unvisited[i].shortestDistFromStart != -1) {
						smallestConnection = unvisited[i];
					}
					if (unvisited[i].shortestDistFromStart != -1
							&& unvisited[i].shortestDistFromStart < smallestConnection.shortestDistFromStart) {
						smallestConnection = unvisited[i];
					}
				}
			}
			currentVertex = smallestConnection;
		}

		try {
			FileWriter fw = new FileWriter(outputFile);

			AdjList current = endVertex;
			int cnt = 0;
			while (current != startVertex.prevAdjList) {
				current = current.prevAdjList;
				cnt++;
			}

			fw.write(cnt + " " + (cnt - 1) + "\n");
			AdjList current1 = endVertex;
			while (current1 != startVertex.prevAdjList) {
				fw.write(current1.vertexName + " " + current1.latitude + ", " + current1.longitude + "\n");
				current1 = current1.prevAdjList;
			}
			current1 = endVertex;
			for (int i = 0; i < cnt - 1; i++) {
				fw.write(i + " " + (i + 1) + " " + current1.prevEdge.roadName + "\n");
				current1 = current1.prevAdjList;
			}
			fw.close();
			
			System.out.println("Finished! Please check output file for results.");
		} catch (Exception e) {
			System.out.println("Exception caught!");
			System.out.println(e.getMessage());
		}

	}
}
