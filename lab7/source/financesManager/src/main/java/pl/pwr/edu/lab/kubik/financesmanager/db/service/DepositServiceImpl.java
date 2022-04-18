package pl.pwr.edu.lab.kubik.financesmanager.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Deposit;
import pl.pwr.edu.lab.kubik.financesmanager.db.repository.DepositRepository;

@Service
@Transactional
public class DepositServiceImpl implements DepositService{
    private final DepositRepository depositRepository;

    @Autowired
    public DepositServiceImpl(DepositRepository depositRepository) {
        this.depositRepository = depositRepository;
    }

    @Override
    public void addDeposit(Deposit deposit) {
        depositRepository.save(deposit);
    }
}
