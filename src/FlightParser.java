import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FlightParser {
    private Scanner s;
    private QuickestFlight a;
    private DestinationsByFlight b;
    private MaxRoundTrip c;

    /*
    * Constructor With Path
     */
    public FlightParser (String path) throws IOException {
        String fileName = path;
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
    public String calculate(){

    }

}
