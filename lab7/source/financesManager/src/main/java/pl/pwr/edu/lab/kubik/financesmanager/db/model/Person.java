package pl.pwr.edu.lab.kubik.financesmanager.db.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer personId;
    private String name, surname;

    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Person() {}
}
