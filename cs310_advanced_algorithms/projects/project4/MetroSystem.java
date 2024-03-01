
// For cs310 pa4 Boston metro system
// The MetroSystem object 
// Note that this does not perfectly represent the little square at the heart of the system
// because the notion of inbound and outbound turn over there, confusing the load code.
// I'll try to fix this as soon as possible
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import edu.princeton.cs.algs4.*;

public class MetroSystem {
	// The platforms, one for each vertex, i.e. app info for that vertex
	private Platform[] platforms = null;
	//private Station[] stations = null;
	private TreeMap<String, Station> stationMap = null;
	// The graph, made available by getGraph()
	private Digraph platformGraph;
	private Graph stationGraph;

	public MetroSystem(String filePath) {
		int nV = countVertices(filePath);
		platformGraph = new Digraph(2 * nV);
		platforms = new Platform[2 * nV];
		stationMap = new TreeMap<String, Station>();
		// handle file-not-found here, for convenience of caller
		// (like SymbolGraph in S&W)
		try {
			System.out.println("calling fillGraph...");
			fillPlatformGraph(filePath); // including fake vertex 0
		} catch (FileNotFoundException e) {
			System.err.println("File not found: " + filePath);
			return;
		}
		// connect the vertices for one station all together
		// with edges for (pedestrian) transfers
		for (String s : stationMap.keySet()) {
			// Find all vertices for station
			Set<Integer> ids = new HashSet<Integer>();
			for (int i = 2; i < platformGraph.V(); i++) {
				// System.out.println("i = " + i);
				if (platforms[i].getStation().getStationName().equals(s))
					ids.add(i);
			}
			if (ids.size() > 1) {			
				// case of multiple platforms for a station: link together
				// to represent possible connections for riders to take
				for (int i : ids)
					for (int j : ids)
						if (i != j)
							platformGraph.addEdge(i, j);
			}
		}
		System.out.println("orig graph #edges =" + platformGraph.E());
		deDupPlatformGraph();
		System.out.println("after dedup, graph #edges =" + platformGraph.E());
		buildStationGraph();
	}
	
	public StationGraph getStationGraph( ) {
		return new StationGraph(stationGraph, stationMap);
	}
	
	public PlatformGraph getPlatformGraph() {
		return new PlatformGraph(platformGraph, platforms);
	}
	
	// provide Platform object for this vertex number
	
	// private to MetroSystem for its use
	// clients should call PlatformGraph.platformOf, since Platforms
	// are the nodes of the PlatformGraph
	private Platform platformOf(int id) {
		if (id > 1 && id < platforms.length) {
			return platforms[id];
		} else
			return null;
	}

	private int countVertices(String filePath) {
		int nV = 1; // one for spot 0, not used
		try {
			Scanner in = null;
			in = new Scanner(new File(filePath));
			while (in.hasNextLine()) {
				nV++;
				in.nextLine();
			}
		} catch (FileNotFoundException e) {
			System.err.println("File not found: " + filePath);
			return 0;
		}
		return nV;
	}

	// Helper to constructor: fill platforms array
	// while building graph from file data
	// This constructs a graph with many duplicate edges, because it
	// uses the file data to see all the connections in and out from each station
	// in order to pick up things like line-splits at JFK/UMass
	// The caller calls deDup to clean it up
	private void fillPlatformGraph(String filePath) throws FileNotFoundException {
		Scanner in = null;
		in = new Scanner(new File(filePath));
		// graph can't have vertex 0, so start from 2, using 2 vertices per input line
		// of file
		// placeholder for spot vertex 0
		platforms[0] = new Platform(null, "fake vertex", 0);
		platforms[1] = new Platform(null, "fake vertex", 0);
//		Set<Integer> nodeIds0 = new HashSet<Integer> ();
//		nodeIds0.add(98);
//		nodeIds0.add(100);
//		nodeIds0.add(120);
		// stations[0] = new Station("fake vertex", 0,0,0);
		int platformId = 0; // soon inc'd to 2
		int stationId = 0; // soon inc'd to 1
		while (in.hasNextLine()) {
			platformId += 2; // start from 2, use 2 Platforms per input line, one for ea direction
			// stationId++;
			double lat = 0.0, lon = 0.0;
			String line1 = in.nextLine();
			String[] tokens = line1.split(",");
			int thisFileId = Integer.parseInt(tokens[0]);
			int thisPlatformId = platformId;
			// System.out.println("doing station " + tokens[1] + " id " + thisId);
			assert thisFileId == thisPlatformId / 2 : "bad line Id: expect ids in order in file";
			if (tokens.length > 5 && !tokens[5].isEmpty())
				lat = Double.parseDouble(tokens[5]);
			if (tokens.length > 6 && !tokens[6].isEmpty())
				lon = Double.parseDouble(tokens[6]);
			String stationName = tokens[1];
			String trainLine = tokens[2];
			Station station = stationMap.get(stationName);
			if (station == null) {
				stationId++;
				station = new Station(stationName, lat, lon, stationId);
				stationMap.put(stationName, station);
			}
			station.addTrainLine(trainLine);
			platforms[thisPlatformId] = new Platform(station, trainLine, thisPlatformId);
			platforms[thisPlatformId + 1] = new Platform(station, trainLine, thisPlatformId + 1);
			int neighborFileId1 = Integer.parseInt(tokens[3]);
			int neighborPlatformId1 = 2 * neighborFileId1; // even side: this is even, so is thisPlatformId
			int neighborFileId2 = Integer.parseInt(tokens[4]);
			int neighborPlatformId2 = 2 * neighborFileId2;
			if (neighborFileId1 > 0) { // has connection in this direction
				platformGraph.addEdge(thisPlatformId, neighborPlatformId1);
				platformGraph.addEdge(neighborPlatformId1+1, thisPlatformId + 1); // other track (in other dir)
			}
			if (neighborFileId2 > 0) { //  has conn in other direction							
				platformGraph.addEdge(neighborPlatformId2, thisPlatformId);
				platformGraph.addEdge(thisPlatformId+1, neighborPlatformId2+1);	// other track, other dir
			}
		}
		in.close();
	}

	// Check that an edge between x and y would be reasonable.
	// Edges should be between platforms on the same-color trainLine or
	// platforms at the same station, so check if neither is true
	// Note this allow a RedA station to connect directly to a Red station
	// because that's just following a split, no rider transfer needed.
	// The first three characters of a trainLine String give the trainline
	// grouping: Red, Gre, Blu, Ora, Sil i.e. the trainline color
	private void ckPlatformEdge(int x, int y) {
		// check edge: OK to match RedA with Red and GreenC with Green
		// i.e. match on trainLineColor
		// since these connections allow trains to go through, no transfer
		try {
			if ((x&1) != (y&1) && !platforms[x].getStation().getStationName()
					.equals(platforms[y].getStation().getStationName())) {
				System.out.println("Data error/bug: edge connects even platform to odd platform at another station");	
			}
			if (!platforms[x].getStation().getStationName()
					.equals(platforms[y].getStation().getStationName())
					&& !platforms[x].getTrainLineColor()
					.equals(platforms[y].getTrainLineColor())) {
				System.out.println("Data error/bug: edge connects different stations on different-color lines: "
						+ platforms[x].getStation().getStationName() + " id " 
						+ x + " on " + platforms[x].getTrainLine()
						+ " and  " + platforms[y].getStation().getStationName() + " id "
						+ y + " on " + platforms[y].getTrainLine());
			}
		} catch (Exception e) {
			System.out.println("ckEdge: exception at x, y " + x + "," + y + e);
			System.out.println("stations: " + platforms[x].getStation().getStationName() + ","
					+ platforms[y].getStation().getStationName());
		}
	}

	private void buildStationGraph() {
		stationGraph = new Graph(stationMap.size() + 1);
		// collect up wanted edges in a Set that will reject duplicates
		Set<SimpleUndirEdge> edges = new HashSet<SimpleUndirEdge>();
		for (int i = 2; i < platformGraph.V(); i++) {
			for (int j : platformGraph.adj(i)) {
				/* edge i-> j exists in platform graph */
				int x = platformOf(i).getStation().getStationId();
				int y = platformOf(j).getStation().getStationId();
				if (x != y) // don't include in-station transfers
					edges.add(new SimpleUndirEdge(x, y));
			}
		}
		// now use the dedup'd set of edges to create the graph
		for (SimpleUndirEdge e : edges) {
			stationGraph.addEdge(e.x, e.y);
		}

	}

	// named SimpleEdge to be different from Edge of book
	// edge for directed graph: SimpleEdge(x,y) and SimpleEdge<y,x) are different
	private class SimpleEdge {
		int x, y;

		SimpleEdge(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean equals(Object other) {
			// code on pg. 103, adapted
			if (this == other)
				return true;
			if (other == null)
				return false;
			if (this.getClass() != other.getClass())
				return false;
			SimpleEdge o = (SimpleEdge) other;
			return x == o.x && y == o.y;
		}

		@Override
		public int hashCode() {
			return Integer.valueOf(x).hashCode() >> 16 | Integer.valueOf(y).hashCode();
		}

	}

	// edge for undirected graph: SimpleEdge(x,y) and SimpleEdge<y,x) are .equals
	private class SimpleUndirEdge {
		int x, y;

		SimpleUndirEdge(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean equals(Object other) {
			// code on pg. 103, adapted
			if (this == other)
				return true;
			if (other == null)
				return false;
			if (this.getClass() != other.getClass())
				return false;
			SimpleUndirEdge o = (SimpleUndirEdge) other;
			return x == o.x && y == o.y || x == o.y && y == o.x;
		}

		@Override
		public int hashCode() { // needs (x,y) and (y,x) to have same hashCode
			return Integer.valueOf(x).hashCode() | Integer.valueOf(y).hashCode();
		}

		@Override
		public String toString() {
			return "(" + x + "," + y + ")";
		}

	}

	// de-duplicate edges by using a Set of directed edges
	// Rebuild G using dedup'd edges
	private void deDupPlatformGraph() {
		Set<SimpleEdge> edges = new HashSet<SimpleEdge>();
		for (int i = 1; i < platformGraph.V(); i++) {
			for (int j : platformGraph.adj(i)) {
				if (i != j) {
					edges.add(new SimpleEdge(i, j));
				} else {
					System.out.println("Unexpected i<->i edge, i = " + i);
				}
			}
		}
		platformGraph = new Digraph(platformGraph.V()); // recreate it
		for (SimpleEdge e : edges) {
			ckPlatformEdge(e.x, e.y);
			platformGraph.addEdge(e.x, e.y);
		}
	}

	
	// find all train lines in system
	public Set<String> findAllLines() {
		Set<String> lines = new TreeSet<String>();
		for (int i = 2; i < platformGraph.V(); i++) {
			Platform p = platformOf(i);
			lines.add(p.getTrainLine());
		}
		return lines;
	}

	public static void main(String[] args) {
		MetroSystem mG = new MetroSystem(args[0]);
		Set<String> lines = mG.findAllLines();
		System.out.println("Train Lines: " +lines);
		StationGraph stationGraph = mG.getStationGraph();
		Station s = stationGraph.stationOf(10);
		System.out.println("station 10: " + s.getStationName());
	}
}


