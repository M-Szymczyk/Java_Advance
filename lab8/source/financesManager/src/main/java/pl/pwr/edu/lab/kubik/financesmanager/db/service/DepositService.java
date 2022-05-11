package pl.pwr.edu.lab.kubik.financesmanager.db.service;

import org.springframework.stereotype.Service;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Deposit;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Event;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Instalment;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Person;

import java.io.File;
import java.util.List;

@Service
public interface DepositService {
    void addDeposit(Deposit deposit);

    void loadCsv(File file);

    List<Deposit> getAll();

    boolean existsByRepaymentAndAndPersonID(Instalment instalment, Person person);

    void deleteAll();
    Deposit getDepositByDepositId(Integer id);

    Integer getDepositByDateAndAmountAndPersonAndEventAndInstalment(Long date, Integer amount, Person person,
                                                                    Event event, Instalment instalment);

}
