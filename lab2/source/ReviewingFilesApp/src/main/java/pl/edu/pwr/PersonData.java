package pl.edu.pwr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class PersonData {
    String name, surname, course, university;
    int age;
    BufferedImage myPicture;

    public PersonData(String name, String surname, String course, String university, int age, BufferedImage myPicture) {
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
}
