package pl.pwr.edu.lab.kubik.financesmanager.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    Event getEventByEventId(Integer integer);
    Event getEventByNameAndPlaceAndDate(String name, String place, Long date);
}