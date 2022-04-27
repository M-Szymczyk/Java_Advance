package pl.pwr.edu.lab.kubik.financesmanager.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Event;
import pl.pwr.edu.lab.kubik.financesmanager.db.repository.EventRepository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void addEvent(Event event) {
        eventRepository.save(event);
    }

    @Override
    public void loadCsv(File file) {
        var calendar = Calendar.getInstance();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine();
            List<String[]> list = new ArrayList<>();
            reader.lines().forEach(s -> list.add(s.split(";")));
            list.forEach(strings -> {
                var date = LocalDate.parse(strings[2],
                        DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                calendar.set(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
                eventRepository.save(new Event(strings[0], strings[1],
                        calendar.get(Calendar.MINUTE)));
            });

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Event> getAll() {
        return eventRepository.findAll();
    }
}
