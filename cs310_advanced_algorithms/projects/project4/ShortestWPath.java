import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DijkstraSP;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;

import java.util.*;

public class ShortestWPath {
    private PlatformGraph pg;
    private int src;
    DijkstraSP djkSP;

    public ShortestWPath(PlatformGraph pg, int src) {
        this.pg = pg;
        this.src = src;

        // conversion for dijkstraSP
        EdgeWeightedDigraph ewdg = convertGraph(pg);
        djkSP = new DijkstraSP(ewdg, this.src);
    }

    public Iterable<DirectedEdge> pathTo(int platform) {
        return djkSP.pathTo(platform);
    }

    private EdgeWeightedDigraph convertGraph(PlatformGraph pg) {
        Digraph dg = pg.getDigraph();
        DirectedEdge de;
        int v = dg.V();
        EdgeWeightedDigraph ewdg = new EdgeWeightedDigraph(v);

        // store all directed edges made
        Set<DirectedEdge> dEdgesSet = new HashSet<>();

        // create directed edges from digraph
        for (int i = 2; i < v; i++) {
            for (int adj : dg.adj(i)) {
                int weight = cost(i, adj);
                de = new DirectedEdge(i, adj, weight);
                dEdgesSet.add(de);
            }
        }

        // make edge weighted graph & return it
        for (DirectedEdge e : dEdgesSet) {
            ewdg.addEdge(e);
        }
        return ewdg;
    }

    public void printRoute(Iterable<DirectedEdge> path, PlatformGraph platformGraph) {
        // filter out repeated stations because of edges
        List<Station> stationList = new ArrayList<>();
        for (DirectedEdge de : path) {
            int lastPlatform = de.to();
            Station station = platformGraph.platformOf(lastPlatform).getStation();
            if (!stationList.contains(station)) {
                stationList.add(station);
            }
        }

        // print route
        for (Station s : stationList) {
            System.out.printf("%s %s\n", s.getStationName(), s.getTrainLines());
        }
        System.out.println();
    }

    // get the weight of each edge
    private int cost(int station, int adj) {
        int opp = pg.oppositePlatformOf(station);
        int adjPlatform = pg.platformOf(adj).getPlatformId();

        // platform transfer
        if (opp == adjPlatform) {
            return 7;
        }

        // silver line
        String adjLine = pg.platformOf(adj).getTrainLine();
        if (adjLine.equals("Silver")) {
            return 3;
        }

        // normal
        return 1;
    }

    public static void main(String[] args) {
        MetroSystem mSys = new MetroSystem(args[0]);
        PlatformGraph platformGraph = mSys.getPlatformGraph();

        // start
        int jfk = platformGraph.platformOf("JFK/UMass");

        // end
        int wLand = platformGraph.platformOf("Wonderland");

        // get best path
        ShortestWPath swPath = new ShortestWPath(platformGraph, jfk);
        Iterable<DirectedEdge> path = swPath.pathTo(wLand);

        // print best path
        System.out.println("\n*** Best Route ***");
        swPath.printRoute(path, platformGraph);
    }
}
