package pl.pwr.edu.lab.kubik.financesmanager.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Deposit;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Event;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Instalment;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Person;
import pl.pwr.edu.lab.kubik.financesmanager.db.repository.DepositRepository;
import pl.pwr.edu.lab.kubik.financesmanager.db.repository.EventRepository;
import pl.pwr.edu.lab.kubik.financesmanager.db.repository.InstalmentRepository;
import pl.pwr.edu.lab.kubik.financesmanager.db.repository.PersonRepository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional
public class DepositServiceImpl implements DepositService {
    private final DepositRepository depositRepository;
    private final PersonRepository personRepository;
    private final EventRepository eventRepository;
    private final InstalmentRepository instalmentRepository;

    @Autowired
    public DepositServiceImpl(DepositRepository depositRepository, PersonRepository personRepository,
                              EventRepository eventRepository, InstalmentRepository instalmentRepository) {
        this.depositRepository = depositRepository;
        this.personRepository = personRepository;
        this.eventRepository = eventRepository;
        this.instalmentRepository = instalmentRepository;
    }


    @Override
    public void addDeposit(Deposit deposit) {
        depositRepository.save(deposit);
    }

    @Override
    public void loadCsv(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine();
            List<String[]> list = new ArrayList<>();
            reader.lines().forEach(s -> list.add(s.split(";")));
            list.forEach(strings -> {
                Calendar cal = Calendar.getInstance();
                cal.setTime(Date.valueOf(LocalDate.parse(strings[0],
                        DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
                depositRepository.save(new Deposit(
                        personRepository.getById(Integer.parseInt(strings[2])),
                        eventRepository.getEventByEventId(Integer.parseInt(strings[3])),
                        instalmentRepository.getByInstalmentId(Integer.parseInt(strings[4])), cal.getTimeInMillis(),
                        Integer.parseInt(strings[1])));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Deposit> getAll() {
        return depositRepository.findAll();
    }

    @Override
    public boolean existsByRepaymentAndAndPersonID(Instalment instalment, Person person) {
        return depositRepository.existsByInstalmentAndPerson(instalment,person);
    }

    @Override
    public void deleteAll() {
        depositRepository.deleteAll();
    }

    @Override
    public Deposit getDepositByDepositId(Integer id) {
        return depositRepository.getDepositByDepositId(id);
    }

    @Override
    public Integer getDepositByDateAndAmountAndPersonAndEventAndInstalment(Long date, Integer amount, Person person, Event event, Instalment instalment) {
        return depositRepository.getDepositByDateAndAmountAndPersonAndEventAndInstalment(date,amount,person,event,instalment);
    }
}
