package pl.edu.pwr.reviewingfilesapplication.person;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.WeakHashMap;

public class FileLoader {
    private final Path pathToDirectory;
    public static void main(String[] args) {
        WeakHashMap<String, PersonData> weakHashMap = new WeakHashMap<>();
        Path path = Path.of("C:\\Users\\Hyperbook\\Desktop\\JavaZaw\\mszymczyk_248881_java\\lab2\\release\\test\\248881");
        FileLoader fileLoader = new FileLoader(path);
        try {
            PersonData personData = fileLoader.readFromRecordFile();
            System.out.println("1."+personData);
            weakHashMap.put(path.toFile().getName(),personData);
            System.gc();
            System.out.println("2."+(weakHashMap.get(path.toFile().getName())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileLoader(Path pathToDirectory) {
        this.pathToDirectory = pathToDirectory;
    }



    public PersonData readFromRecordFile() throws IOException {
        File file = new File(pathToDirectory.toFile() + "\\record.txt");
        String text;
        Scanner myReader = new Scanner(file);
        //while (myReader.hasNextLine()) {
        String[] data = myReader.nextLine().split(";");
        myReader.close();
        return new PersonData(data[0], data[1], data[3], data[4], Integer.parseInt(data[2]),
                new Image( new FileInputStream(new File(pathToDirectory + "\\image.png"))));
        //}

    }
}
