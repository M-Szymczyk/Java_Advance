package pl.pwr.edu.lab.kubik.financesmanager.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Instalment;
import pl.pwr.edu.lab.kubik.financesmanager.db.repository.EventRepository;
import pl.pwr.edu.lab.kubik.financesmanager.db.repository.InstalmentRepository;

import java.io.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class InstalmentServiceImpl implements InstalmentService {
    private final InstalmentRepository instalmentRepository;
    private final EventRepository eventRepository;

    @Autowired
    public InstalmentServiceImpl(InstalmentRepository instalmentRepository, EventRepository eventRepository) {
        this.instalmentRepository = instalmentRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public void addInstalment(Instalment instalment) {
        instalmentRepository.save(instalment);
    }

    @Override
    public void loadCsv(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine();
            List<String[]> list = new ArrayList<>();
            reader.lines().forEach(s -> list.add(s.split(";")));
            list.forEach(strings -> instalmentRepository.save(new Instalment(
                    eventRepository.getEventByEventId(Integer.parseInt(strings[0])),
                    Integer.parseInt(strings[1]),
                    Date.valueOf(LocalDate.parse(strings[2],
                            DateTimeFormatter.ofPattern("dd.MM.yyyy"))).toString(),
                    Integer.parseInt(strings[3]))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
