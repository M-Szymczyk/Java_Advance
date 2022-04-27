package pl.pwr.edu.lab.kubik.financesmanager.db.service;

import org.springframework.stereotype.Service;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Instalment;

import java.io.File;

@Service
public interface InstalmentService {
    void addInstalment(Instalment instalment);

    void loadCsv(File file);
}
