package pl.pwr.edu.lab.kubik.financesmanager.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Deposit;

public interface DepositRepository extends JpaRepository<Deposit, Integer> {
}