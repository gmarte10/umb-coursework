import edu.princeton.cs.algs4.BreadthFirstPaths;

import java.util.Iterator;

public class ShortestPath {
    public static void main(String[] args) {
        // file
        String filePath = args[0];

        // map & graph
        MetroSystem mS = new MetroSystem(filePath);
        StationGraph stationGraph = mS.getStationGraph();
        Station jfk = stationGraph.stationOf("JFK/UMass");

        // bfs with jfk as src
        BreadthFirstPaths bfs = new BreadthFirstPaths(stationGraph.getGraph(), jfk.getStationId());

        // destinations
        Station wLand = stationGraph.stationOf("Wonderland");
        Station bowdoin = stationGraph.stationOf("Bowdoin");

        // best paths for each destination
        Iterable<Integer> pathToWLand = bfs.pathTo(wLand.getStationId());
        Iterable<Integer> pathToBowdoin = bfs.pathTo(bowdoin.getStationId());

        // print JFK
        System.out.println("\n*** shortest path to Wonderland from JFK/UMass ***");
        for (int i : pathToWLand) {
            System.out.printf("%s %s\n", stationGraph.stationOf(i).getStationName(),
                    stationGraph.stationOf(i).getTrainLines());
        }
        // print Bowdoin
        System.out.println("\n*** shortest path to Bowdoin from JFK/UMass ***");
        for (int i : pathToBowdoin) {
            System.out.printf("%s %s\n", stationGraph.stationOf(i).getStationName(),
                    stationGraph.stationOf(i).getTrainLines());
        }
    }
}
