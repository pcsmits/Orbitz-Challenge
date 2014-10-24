import java.io.IOException;

public class FlightDriver {
	public static void main(String[] args) throws IOException{
        FlightParser p = new FlightParser();
        FlightWriter w = new FlightWriter();
        Optimizer o;

        while(p.hasNext()){
            o = p.getObject();
            String result = o.calculate();
            w.write(result);
        }
    }
}
