package pl.pwr.edu.lab.kubik.financesmanager.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Event;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Instalment;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Person;

import java.util.List;

@Repository
public interface InstalmentRepository extends JpaRepository<Instalment, Integer> {
    Instalment getByInstalmentId(Integer instalmentId);

    List<Instalment> getByDateEquals(Long date);

    List<Instalment> getByDateBefore(Long date);

    Integer getInstalmentIdByEventAndNoInstalmentAndDateAndAmount(Event event, int number, Long date, int amount);
}