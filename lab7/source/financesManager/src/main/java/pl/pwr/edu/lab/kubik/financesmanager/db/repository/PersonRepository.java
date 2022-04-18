package pl.pwr.edu.lab.kubik.financesmanager.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Person;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person,Integer> {
    List<Person> findByName(String name);

}
