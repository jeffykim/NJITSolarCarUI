package edu.njit.solarcar.telemetry.CanTest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class FileRead
{
    private Scanner coordsReader;
    private InputStream coordsFile;
    private ArrayList<XYCoordinate> coordsList;

    public FileRead(InputStream coordsFile) throws IOException {
        coordsReader = new Scanner(coordsFile);
        this.coordsFile = coordsFile;
        coordsList = new ArrayList<>();

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
