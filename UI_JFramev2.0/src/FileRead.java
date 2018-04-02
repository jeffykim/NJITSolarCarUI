import java.io.File; 
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileRead
{
    private Scanner coordsReader;
    private File coordsFile;
    private ArrayList<XYCoordinate> coordsList;

    public FileRead(File coordsFile) throws IOException {
        coordsReader = new Scanner(coordsFile);
        this.coordsFile = coordsFile;
        coordsList = new ArrayList<XYCoordinate>();

        //parse out everything
        String line;
        while (coordsReader.hasNextLine()) {
            line = coordsReader.nextLine();
            coordsList.add(new XYCoordinate(line));
        }
    }

    public ArrayList<XYCoordinate> getCoordsList() {
        return coordsList;
    }
}
