import org.jgraph.graph.Edge;
import org.jgrapht.alg.cycle.DirectedSimpleCycles;
import org.jgrapht.alg.cycle.TarjanSimpleCycles;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.alg.*;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.traverse.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class Optimizer {
    private Scanner s;
    private DefaultDirectedWeightedGraph<String, DefaultWeightedEdge> g;

    public Optimizer() throws IOException{
        g = new DefaultDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);

       // String fileName = "/home/pcsmits/git/Orbitz-Challenge/src/flights.txt";
        //Path path = Paths.get(fileName);
        URL url = getClass().getResource("flights.txt");
        File path = new File(url.getPath());
        //File path = new File("./flights.txt");
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
        return strPath;
    }

    /*
     * Finding All Destination with number of flights
     */
    public String calculate(String start, int flights){
        return listToString(destinationByFlights(start, flights), ",");
    }

    /*
     * Finding Max Round Trip
     */
    public String calculate(String start){
        List<String> maxTrip = maxRoundTrip(start);
        return listToString(maxTrip, ",");
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

    private List<String> destinationByFlights(String start, int flights){
        Set<String> destinations = new HashSet<String>();
        Set<String> tmp = new HashSet<String>();
        destinations.add(start);

        while(flights > 0){
            flights--;
            for (String vertex : destinations){
                Set<DefaultWeightedEdge> hop = g.edgesOf(vertex);
                for (DefaultWeightedEdge edge : hop) {
                /* foreach edge get the target and recurse by num flights */
                    if(vertex.equals(g.getEdgeSource(edge))) {
                        start = g.getEdgeTarget(edge);
                        tmp.add(start);
                    }
                }
            }
            destinations = new HashSet<String>(tmp);
            tmp.clear();
        }
        List<String> destination = new ArrayList<String>(destinations);
        Collections.sort(destination);
        return destination;
    }

    private List<String> maxRoundTrip(String start){
        DirectedSimpleCycles<String, DefaultWeightedEdge> cycles = new TarjanSimpleCycles<String, DefaultWeightedEdge>(g);
        List<List<String>> roundTrip = cycles.findSimpleCycles();
        int index = -1;
        int length = 0;
        for (int i = 0; i < roundTrip.size(); i++) {
            if(roundTrip.get(i).get(0).contains(start)){
                if(roundTrip.get(i).size() > length){
                    length = roundTrip.get(i).size();
                    index = i;
                }
            }
        }
        roundTrip.get(index).add(start);
        return roundTrip.get(index);
    }
    private String listToString(List arlist, String delimiter) {
        StringBuilder arlstTostr = new StringBuilder();
        if (arlist.size() > 0) {
            arlstTostr.append(arlist.get(0));
            for (int i=1; i<arlist.size(); i++) {
                arlstTostr.append(delimiter);
                arlstTostr.append(arlist.get(i));
            }
        }
        return arlstTostr.toString();
    }
}
