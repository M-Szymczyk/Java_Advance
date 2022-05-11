package pl.pwr.edu.lab.kubik.financesmanager.db.service;

import org.springframework.stereotype.Service;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Event;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public interface EventService {
    void addEvent(Event event);

    void loadCsv(File file);

    List<Event> getAll();

    void deleteAll();

    Event getEventByEventId(Integer eventID);

    Integer getEventByNameAndPlaceAndDate(String name, String place, Long date);
}
