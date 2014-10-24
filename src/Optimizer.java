import org.jgraph.graph.Edge;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.alg.*;
import java.net.*;
import org.jgrapht.*;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;



public class Optimizer {
    private QuickestFlight a;
    private DestinationsByFlight b;
    private MaxRoundTrip c;
    private Scanner s;
    private DefaultDirectedWeightedGraph<String, DefaultWeightedEdge> g;

    public Optimizer() throws IOException{
        g = new DefaultDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        String fileName = "flights.txt";
        Path path = Paths.get(fileName);
        s = new Scanner(path);

        s.useDelimiter(System.getProperty("line.separator"));
        flights();
    }

    /*
    * Finding Quickest flight between two destinations
     */
    public String calculate(String start, String destination){

        return "";
    }

    /*
    * Finding All Destination with number of flights
     */
    public String calculate(String start, int flights){

        return "";
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

    private String addFlight(String line){
        String[] elements = line.split(",");
        String v1 = elements[0];
        String v2 = elements[1];
        Integer i = Integer.parseInt(elements[2]);

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


        return "";
    }
}
