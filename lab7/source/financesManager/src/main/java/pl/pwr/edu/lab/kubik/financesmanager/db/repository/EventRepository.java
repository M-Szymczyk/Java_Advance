package pl.pwr.edu.lab.kubik.financesmanager.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Event;

public interface EventRepository extends JpaRepository<Event, Integer> {
}