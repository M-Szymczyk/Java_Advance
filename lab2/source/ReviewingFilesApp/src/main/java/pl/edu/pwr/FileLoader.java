package pl.edu.pwr;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class FileLoader {
    public static void main(String[] args) {
        FileLoader fileLoader = new FileLoader(Path.of("C:\\Users\\Hyperbook\\Desktop\\JavaZaw\\mszymczyk_248881_java\\lab2\\release\\test\\248881"));
        try {
            PersonData personData = fileLoader.readFromRecordFile();
            System.out.println(personData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileLoader(Path pathToDirectory) {
        this.pathToDirectory = pathToDirectory;
    }

    private final Path pathToDirectory;

    private PersonData readFromRecordFile() throws IOException {
        File file = new File(pathToDirectory.toFile() + "\\record.txt");
        String text;
        Scanner myReader = new Scanner(file);
        //while (myReader.hasNextLine()) {
        String[] data = myReader.nextLine().split(";");
        myReader.close();
        return new PersonData(data[0], data[1], data[3], data[4], Integer.parseInt(data[2]), ImageIO.read(new File(pathToDirectory + "\\image.png")));
        //}

    }
}
