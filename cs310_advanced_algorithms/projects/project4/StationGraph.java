
// For cs310 pa4 Boston metro graph 
import java.util.*;

import edu.princeton.cs.algs4.*;

public class StationGraph {
	private Set<Station> sameLineNeighbors = new HashSet<>();

	private Station[] stations = null;
	private Map<String, Station> stationMap = null;
	// The graph, made available by getGraph()
	private Graph stationGraph;
	private Set<String> s;

	public StationGraph(Graph g, Map<String, Station> sMap) {
		stationGraph = g;
		stationMap = sMap;
		// create stations table for lookup by stationId
		// station ids start at 1, like the file does
		stations = new Station[stationMap.size() + 1];
		for (String s : stationMap.keySet()) {
			Station station = stationMap.get(s);
			stations[station.getStationId()] = station;
		}
	}

	public Graph getGraph() {
		return stationGraph;
	}

	public Station stationOf(int id) {
		if (id > 0 && id < stations.length) {
			return stations[id];
		} else
			return null;
	}

	public Station stationOf(String name) {
		return stationMap.get(name);
	}

	// Report on how this station is directly connected to other stations in the
	// system using the Station graph
	public void printStationNeighbors(String stationName) {
		Station station = stationMap.get(stationName);
		System.out.println("printStationNeighbors for " + stationName + ", id " + station.getStationId() + " train lines "
				+ station.getTrainLines());
		for (int i : stationGraph.adj(station.getStationId())) {
			System.out.println("Neighbor station: " + stations[i].getStationName() + "id " + i + ", train lines "
					+ stations[i].getTrainLines());
		}
	}

	// Find end of a given train line, that is, the station
	// that has only one neighbor on the same train line
	// Two such stations exist for each line. Return the one further from
	// Government Center.
	public Station endOfLineStation(String line) {
		int size = stationMap.size() + 1;

		// stations on the same line
		for (int i = 1; i < size; i++) {
			Station station = stationOf(i);
			for (int neighbor : stationGraph.adj(station.getStationId())) {
				Set<String> nLines = stationOf(neighbor).getTrainLines();
				for (String l : nLines) {
					if (l.equals(line)) {
						sameLineNeighbors.add(stationOf(neighbor));
					}
				}
			}
		}

		// get the 2 end stations
		int nCount;
		Set<Station> ends = new HashSet<>();
		for (Station s : sameLineNeighbors) {
			nCount = 0;
			for (int neighbor : stationGraph.adj(s.getStationId())) {
				Set<String> nLines = stationOf(neighbor).getTrainLines();
				for (String l : nLines) {
					if (l.equals(line)) {
						nCount++;
					}
				}
			}
			if (nCount == 1) {
				ends.add(s);
			}
		}

		// return farthest end
		int eSize = ends.size();
		if (eSize != 2) {
			System.out.println("Something went wrong...");
		}
		double[] dist = new double[eSize];
		Station[] eArray = new Station[eSize];
		int e = 0;

		for (Station s : ends) {
			dist[e] = s.distanceFromBoston();
			eArray[e] = s;
			e++;
		}
		Station otherEnd = null;
		if (dist[0] > dist[1]) {
			otherEnd = eArray[1];
			return eArray[0];
		}
		else {
			otherEnd = eArray[0];
			return eArray[1];
		}
	}

	// Print stations on a trainline, starting from the end station that is
	// further out from central Boston, using the Station graph
	public void printTrainLine1(String trainLine) {
		System.out.println("printTrainLine1 for " + trainLine);
		// get the end station farthest from central Boston
		Station end = endOfLineStation(trainLine);

		List<Station> entireLine = new ArrayList<>();
		Station current = null;
		Station previous = null;
		int slSize = sameLineNeighbors.size();

		// get stations in order
		current = end;
		entireLine.add(current);

		for (int i = 0; i < slSize - 1; i++) {
			for (int neighbor : stationGraph.adj(current.getStationId())) {
				Set<String> nLines = stationOf(neighbor).getTrainLines();
				if (entireLine.contains(stationOf(neighbor)) || !nLines.contains(trainLine)) {
					continue;
				}
				else {
					entireLine.add(stationOf(neighbor));
					current = stationOf(neighbor);
				}
			}
		}

		// print stations
		System.out.printf("\n*** Stations for %s ***\n", trainLine);
		for (Station s : entireLine) {
			System.out.printf("%s %s\n", s.getStationName(), s.getTrainLines());
		}
		System.out.println();
	}

	public static void main(String[] args) {
		MetroSystem mS = new MetroSystem(args[0]);
		StationGraph stationGraph = mS.getStationGraph();
		stationGraph.printStationNeighbors("JFK/UMass");
		stationGraph.printTrainLine1("Blue");
	}
}
