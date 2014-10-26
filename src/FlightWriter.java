import java.io.PrintWriter;

public class FlightWriter {

    PrintWriter writer;
    public FlightWriter(){
        try {
            writer = new PrintWriter("../out/solutions.txt", "UTF-8");
        } catch (Exception E){

        }
    }
    public void write(String line){
        writer.println(line);
    }
    public void close(){
        writer.close();
    }
}
