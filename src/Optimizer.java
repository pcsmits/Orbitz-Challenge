import org.jgraph.graph.Edge;
import org.jgrapht.alg.cycle.DirectedSimpleCycles;
import org.jgrapht.alg.cycle.TarjanSimpleCycles;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.alg.*;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.traverse.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class Optimizer {
    private Scanner s;
    private DefaultDirectedWeightedGraph<String, DefaultWeightedEdge> g;

    public Optimizer() throws IOException{
        g = new DefaultDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        String fileName = "/home/pcsmits/git/Orbitz-Challenge/src/flights.txt";
        Path path = Paths.get(fileName);
        //System.out.println(path.toString());
        s = new Scanner(path);

        s.useDelimiter(System.getProperty("line.separator"));
        flights();
    }

    /*
     * Finding Quickest flight between two destinations
     */
    public String calculate(String start, String destination){
        String strPath = start;
        List<DefaultWeightedEdge> path = DijkstraShortestPath.findPathBetween(g, start, destination);
        if (path == null) {return "";}
        for (DefaultWeightedEdge edge : path) {
            String target = g.getEdgeTarget(edge);
            strPath += ","+target;
        }
        System.out.println(strPath);
        return strPath;
    }

    /*
     * Finding All Destination with number of flights
     */
    public String calculate(String start, int flights){
        List<String> s = new ArrayList<String>();
        s = destinationByFlights(start, flights, s);
        return s.toString();

    }

    /*
     * Finding Max Round Trip
     */
    public String calculate(String start){
        return "";
    }


    /* Reading in airport data and storing in data structure*/
    private void flights(){
        while(s.hasNext()){
            addFlight(s.next());
        }
    }

    private void addFlight(String line){
        //System.out.println(line);
        String[] elements = line.split(",");
        elements[2] = elements[2].replaceAll("(\\r|\\n)", "");
        int i = Integer.parseInt(elements[2]);
        String v1 = elements[0];
        String v2 = elements[1];

        // add the vertices
        if(!g.containsVertex(v1)){
            g.addVertex(v1);
        }
        if(!g.containsVertex(v2)) {
            g.addVertex(v2);
        }

        // add edges
        DefaultWeightedEdge e = g.addEdge(v1, v2);
        g.setEdgeWeight(e, i);
    }

    private List<String> destinationByFlights(String start, int flights, List<String> destination){
        if(flights > 0) {
            flights--;
            /* get all edges of node */
            Set<DefaultWeightedEdge> hop = g.edgesOf(start);
            for(DefaultWeightedEdge edge : hop){
                /* foreach edge get the target and recurse by num flights */
                String target = g.getEdgeTarget(edge);
                destinationByFlights(target, flights, destination);
            }
        } else {
            destination.add(start);
        }
        return destination;
    }

    private List<String> maxRoundTrip(String start){
        DirectedSimpleCycles<String, DefaultWeightedEdge> cycles = new TarjanSimpleCycles<String, DefaultWeightedEdge>(g);
        List<List<String>> roundTrip = cycles.findSimpleCycles();
        System.out.println(roundTrip.toString());

        return null;
    }
}
