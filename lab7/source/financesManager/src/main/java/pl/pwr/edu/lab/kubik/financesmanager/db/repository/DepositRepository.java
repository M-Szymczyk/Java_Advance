package pl.pwr.edu.lab.kubik.financesmanager.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Deposit;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, Integer> {
}