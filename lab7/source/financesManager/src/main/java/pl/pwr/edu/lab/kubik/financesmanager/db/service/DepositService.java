package pl.pwr.edu.lab.kubik.financesmanager.db.service;

import org.springframework.stereotype.Service;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Deposit;

import java.io.File;
import java.util.List;

@Service
public interface DepositService {
    void addDeposit(Deposit deposit);

    void loadCsv(File file);

    List<Deposit> getAll();
}
