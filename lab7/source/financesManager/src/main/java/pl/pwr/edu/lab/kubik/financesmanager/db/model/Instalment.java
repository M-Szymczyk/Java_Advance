package pl.pwr.edu.lab.kubik.financesmanager.db.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
public class Instalment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer instalmentId;
    @OneToOne
    @JoinColumn(name = "eventId")
    Event event;
    Integer noInstalment, amount;
    Long date;

    public Instalment() {    }


    public Instalment(Event event, Integer noInstalment, Long date, Integer amount) {
        this.event = event;
        this.noInstalment = noInstalment;
        this.date = date;
        this.amount = amount;
    }
}
