package pl.pwr.edu.lab.kubik.financesmanager.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Instalment;

public interface InstalmentRepository extends JpaRepository<Instalment, Integer> {
}