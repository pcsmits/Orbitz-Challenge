import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FlightParser {
    private Scanner s;
    private Optimizer o;

    /*
    * Constructor With Path
     */
    public FlightParser (String file) throws IOException {
        String fileName = file;
        Path path = Paths.get(fileName);
        s = new Scanner(path);

        s.useDelimiter(System.getProperty("line.separator"));
    }

    /*
    * Constructor without path
    * uses default location in this directory
     */
    public FlightParser () throws  IOException {
        String fileName = "task.test";
        Path path = Paths.get(fileName);
        s = new Scanner(path);

        s.useDelimiter(System.getProperty("line.separator"));
    }

    /*
    * checks weather the scanner has another entry
     */
    public Boolean hasNext(){
        if(!s.hasNext()){
            s.close();
            return false;
        }
        return true;
    }

    /*
    * Return String-ified Output from next Entry for writing
     */
    public Optimizer getObject(){
        if(s.hasNext()){
            String line = s.next();
            String[] elements = line.split(",");

            // test if valid string here //

            String start = elements[0];
            String destination;
            int flights;

            /* if only one element then finding maxRoundTrip */
            if(elements.length == 1){
                this.o = new Optimizer(start);
            } else if (Character.isLetter(elements[1].charAt(0))){
                /*if second element is a Letter */
                destination = elements[1];
                this.o = new Optimizer(start, destination);
            } else {
               /* if second element is a number */
                flights = Integer.parseInt(elements[1]);
                this.o = new Optimizer(start, flights);
            }

        } else {
            try {
                throw new NoNextLineException("Method calculate can only be done with Valid line, use FlightParser.hasNext() before use");
            } catch(NoNextLineException E){
                E.printStackTrace();
            }
        }


        return null;
    }

}
