package pl.pwr.edu.lab.kubik.financesmanager.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Person;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person,Integer> {
    List<Person> findByName(String name);

    Person getByPersonId(Integer personId);

    Integer getByNameAndSurname(String name,String surname);
}
