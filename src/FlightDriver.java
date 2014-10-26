import java.io.IOException;

public class FlightDriver {
	public static void main(String[] args) throws IOException{
        FlightParser p = new FlightParser();
        FlightWriter w = new FlightWriter();

        while(p.hasNext()){
            String result = p.getNext();
            w.write(result);
        }
        w.close();
    }
}
