package pl.pwr.edu.lab.kubik.financesmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Deposit;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Event;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Instalment;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Person;
import pl.pwr.edu.lab.kubik.financesmanager.db.repository.DepositRepository;
import pl.pwr.edu.lab.kubik.financesmanager.db.repository.EventRepository;
import pl.pwr.edu.lab.kubik.financesmanager.db.repository.InstalmentRepository;
import pl.pwr.edu.lab.kubik.financesmanager.db.repository.PersonRepository;
import pl.pwr.edu.lab.kubik.financesmanager.db.service.DepositService;
import pl.pwr.edu.lab.kubik.financesmanager.db.service.EventService;
import pl.pwr.edu.lab.kubik.financesmanager.db.service.InstalmentService;
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
    private final PersonRepository personRepository;
    private final EventService eventService;
    private final EventRepository eventRepository;
    private final InstalmentService instalmentService;
    private final InstalmentRepository instalmentRepository;
    private final DepositService depositService;
    private final DepositRepository depositRepository;
    public static void main(String[] args) {
        SpringApplication.run(FinancesManagerApplication.class, args);
    }
    @Autowired
    public FinancesManagerApplication(PersonService personService, PersonRepository personRepository,
                                      EventService eventService, EventRepository eventRepository,
                                      InstalmentService instalmentService, InstalmentRepository instalmentRepository,
                                      DepositService depositService, DepositRepository depositRepository) {
        this.personService = personService;
        this.personRepository = personRepository;
        this.eventService = eventService;
        this.eventRepository = eventRepository;
        this.instalmentService = instalmentService;
        this.instalmentRepository = instalmentRepository;
        this.depositService = depositService;
        this.depositRepository = depositRepository;
    }


//    public FinancesManagerApplication(PersonService personService, PersonRepository repository) {
//        this.personService = personService;
//        this.personRepository = repository;
//    }


   // public FinancesManagerApplication(PersonService personService) {
//        this.personService = personService;
//    }

    @Override
    public void run(String... args) throws Exception {
        //Gui.main(args);
        //given
        Person person = new Person("personTest","personSurnameTest");
        personService.addPerson(person);
//        //when
//        var result = personRepository.findByName("personTest");
//        //then
//        for (Person person1 : result) {
//            System.out.println(person1.toString());
//        }
        Event event = new Event("testEvent","place","yesterday");
        eventService.addEvent(event);

        Instalment instalment = new Instalment(event,1,"yes",10);
        instalmentService.addInstalment(instalment);
        Deposit deposit = new Deposit(person,event,instalment,"yesterday",100);
        depositService.addDeposit(deposit);
        for (Event event1 : eventRepository.findAll()) {
            System.out.println(event1.toString());
        }
        for (Deposit deposit1 : depositRepository.findAll()) {
            System.out.println(deposit1.toString());
        }
        for (Instalment instalment1 : instalmentRepository.findAll()) {
            System.out.println(instalment1.toString());
        }
    }
}


