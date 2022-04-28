package pl.pwr.edu.lab.kubik.financesmanager.db.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer depositId;
    @OneToOne
    @JoinColumn(name = "personId")
    Person person;
    @OneToOne
    @JoinColumn(name = "eventId")
    Event event;
    @OneToOne
    @JoinColumn(name = "instalmentId")
    Instalment instalment;
    Integer amount, noPayment;
    Long date;

    public Deposit() {
    }

    public Deposit(Person person, Event event, Instalment instalment, Long date, Integer amount) {
        this.instalment = instalment;
        this.person = person;
        this.event = event;
        this.date = date;
        this.amount = amount;
    }

}
