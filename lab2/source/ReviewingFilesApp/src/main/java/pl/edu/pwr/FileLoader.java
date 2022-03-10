package pl.edu.pwr;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class FileLoader {
    public static void main(String[] args) {

    }

    private Path pathToDirectory;

    private PersonData readFromRecordFile() throws IOException {
        File file = new File(pathToDirectory.toFile() + "//" + "record.txt");
        String text;
        Scanner myReader = new Scanner(file);
        //while (myReader.hasNextLine()) {
        String[] data = myReader.nextLine().split(";");
        myReader.close();
        return new PersonData(data[0], data[1], data[2], data[3], Integer.parseInt(data[4]));
        //}

    }
}
