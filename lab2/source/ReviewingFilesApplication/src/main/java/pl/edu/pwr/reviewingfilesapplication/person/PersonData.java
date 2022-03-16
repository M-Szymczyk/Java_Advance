package pl.edu.pwr.reviewingfilesapplication.person;

import javafx.scene.image.Image;

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
}
