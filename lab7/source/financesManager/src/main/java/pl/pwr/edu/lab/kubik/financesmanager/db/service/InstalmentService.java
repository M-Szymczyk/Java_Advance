package pl.pwr.edu.lab.kubik.financesmanager.db.service;

import org.springframework.stereotype.Service;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Instalment;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Person;

import java.io.File;
import java.sql.Date;
import java.util.List;

@Service
public interface InstalmentService {
    void addInstalment(Instalment instalment);

    void loadCsv(File file);

    List<Instalment> getAll();

    List<Instalment> getAllByPaymentTimeEquals(Long valueOf);

    List<Instalment> getByDateBefore(Long date);

    void deleteAll();

    Instalment getInstallmentById(Integer integer);
}
