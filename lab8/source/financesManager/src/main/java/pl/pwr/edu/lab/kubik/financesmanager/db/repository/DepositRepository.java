package pl.pwr.edu.lab.kubik.financesmanager.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Deposit;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Event;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Instalment;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Person;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, Integer> {
    boolean existsByInstalmentAndPerson(Instalment instalment, Person person);

    Deposit getDepositByDepositId(Integer id);

    Deposit getDepositByDateAndAmountAndPersonAndEventAndInstalment(Long date, Integer amount, Person person,
                                                                    Event event, Instalment instalment);
}