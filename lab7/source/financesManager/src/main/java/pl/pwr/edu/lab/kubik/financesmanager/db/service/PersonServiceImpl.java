package pl.pwr.edu.lab.kubik.financesmanager.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Person;
import pl.pwr.edu.lab.kubik.financesmanager.db.repository.PersonRepository;

@Service
@Transactional
public class PersonServiceImpl implements PersonService{
    private final PersonRepository personRepository;
    @Override
    public Person addPerson(Person person) {
        return personRepository.save(person);
    }

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
}
