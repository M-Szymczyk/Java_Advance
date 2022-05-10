package pl.pwr.edu.lab.kubik.financesmanager.gui.tables;

import javafx.beans.property.SimpleStringProperty;
import lombok.Getter;


public class PersonTable {
    public String getPersonId() {
        return personId.get();
    }

    public SimpleStringProperty personIdProperty() {
        return personId;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getSurname() {
        return surname.get();
    }

    public SimpleStringProperty surnameProperty() {
        return surname;
    }

    SimpleStringProperty personId, name, surname;

    public PersonTable(String personId, String name, String surname) {
        this.personId = new SimpleStringProperty(personId);
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
    }
}
