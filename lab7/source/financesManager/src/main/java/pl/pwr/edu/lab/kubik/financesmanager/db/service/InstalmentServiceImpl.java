package pl.pwr.edu.lab.kubik.financesmanager.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Instalment;
import pl.pwr.edu.lab.kubik.financesmanager.db.repository.InstalmentRepository;

@Service
@Transactional
public class InstalmentServiceImpl implements InstalmentService {
    private final InstalmentRepository instalmentRepository;

    @Autowired
    public InstalmentServiceImpl(InstalmentRepository instalmentRepository) {
        this.instalmentRepository = instalmentRepository;
    }

    @Override
    public void addInstalment(Instalment instalment) {
        instalmentRepository.save(instalment);
    }
}
