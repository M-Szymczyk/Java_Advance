package pl.pwr.edu.lab.kubik.financesmanager.db.service;

import pl.pwr.edu.lab.kubik.financesmanager.db.model.Person;

import java.io.File;
import java.util.List;

public interface PersonService {
    Person addPerson(Person person);
    void loadCsv(File file);
    List<Person> getAll();
}
