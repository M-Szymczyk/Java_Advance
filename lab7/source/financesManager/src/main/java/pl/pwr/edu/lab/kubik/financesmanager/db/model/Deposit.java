package pl.pwr.edu.lab.kubik.financesmanager.db.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer depositId;
    @ManyToOne
    @JoinColumn(name = "personId")
    Person person;
    @ManyToOne
    @JoinColumn(name = "eventId")
    Event event;
    String date;
    Integer amount, noPayment;
}
