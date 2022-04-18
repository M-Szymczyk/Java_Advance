package pl.pwr.edu.lab.kubik.financesmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Person;
import pl.pwr.edu.lab.kubik.financesmanager.db.service.PersonService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.pwr.edu.lab.kubik.financesmanager.gui.Gui;

import static javafx.application.Application.launch;


@SpringBootApplication
public class FinancesManagerApplication implements CommandLineRunner {
    private final PersonService personService;

    public static void main(String[] args) {
        SpringApplication.run(FinancesManagerApplication.class, args);
    }

    @Autowired
    public FinancesManagerApplication(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public void run(String... args) throws Exception {
        Gui.main(args);
//        Person p = new Person();
//        p.setName("name");
//        p.setSurname("surname");
//        personService.addPerson(p);
    }
}


