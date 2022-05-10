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
    private static PersonService personService = null;
    private static final EventService eventService = null;
    private  static final InstalmentService instalmentService = null;
    private static final DepositService depositService = null;
    public static void main(String[] args) {
        SpringApplication.run(FinancesManagerApplication.class, args);
    }

    @Autowired
    public FinancesManagerApplication(PersonService personService) {
        FinancesManagerApplication.personService = personService;
    }





//    public FinancesManagerApplication(PersonService personService, PersonRepository repository) {
//        this.personService = personService;
//        this.personRepository = repository;
//    }


   // public FinancesManagerApplication(PersonService personService) {
//        this.personService = personService;
//    }


    public static PersonService getPersonService() {
        return personService;
    }

    public static EventService getEventService() {
        return eventService;
    }

    public static InstalmentService getInstalmentService() {
        return instalmentService;
    }

    public static DepositService getDepositService() {
        return  depositService;
    }

    @Override
    public void run(String... args) throws Exception {
//        Gui.main(args);
        //Gui.main(args);
        //given
//        Person person = new Person("personTest","personSurnameTest");
//        personService.addPerson(person);
////        //when
////        var result = personRepository.findByName("personTest");
////        //then
////        for (Person person1 : result) {
////            System.out.println(person1.toString());
////        }
//        Event event = new Event("testEvent","place","yesterday");
//        eventService.addEvent(event);
//
//        Instalment instalment = new Instalment(event,1,"yes",10);
//        instalmentService.addInstalment(instalment);
//        Deposit deposit = new Deposit(person,event,instalment,"yesterday",100);
//        depositService.addDeposit(deposit);
//        for (Event event1 : eventRepository.findAll()) {
//            System.out.println(event1.toString());
//        }
//        for (Deposit deposit1 : depositRepository.findAll()) {
//            System.out.println(deposit1.toString());
//        }
//        for (Instalment instalment1 : instalmentRepository.findAll()) {
//            System.out.println(instalment1.toString());
//        }
    }
}


