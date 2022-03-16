package pl.edu.pwr.reviewingfilesapplication.person;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Class contains person's data
 */
public class PersonData {
    public String name, surname, course, university;
    public int age;
    public Image myPicture;

    public PersonData(String name, String surname, String course, String university, int age, javafx.scene.image.Image myPicture) {
        this.name = name;
        this.surname = surname;
        this.course = course;
        this.university = university;
        this.age = age;
        this.myPicture = myPicture;
    }

    @Override
    public String toString() {
        return "PersonData{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", course='" + course + '\'' +
                ", university='" + university + '\'' +
                ", age=" + age +
                '}';
    }

    /**
     * Method read file from specific directory
     * @param pathToDirectory directory have to contain file record.txt and image.txt
     * @return reference to new PersonData class
     * @throws IOException if f.e. files donn't exist
     */
    static public PersonData readFromRecordFile(Path pathToDirectory) throws IOException {
        File file = new File(pathToDirectory.toFile() + "\\record.txt");
        String text;
        Scanner myReader = new Scanner(file);
        //while (myReader.hasNextLine()) {
        String[] data = myReader.nextLine().split(";");
        myReader.close();
        return new PersonData(data[0], data[1], data[3], data[4], Integer.parseInt(data[2]),
                new Image( new FileInputStream((pathToDirectory + "\\image.png"))));
    }
}
