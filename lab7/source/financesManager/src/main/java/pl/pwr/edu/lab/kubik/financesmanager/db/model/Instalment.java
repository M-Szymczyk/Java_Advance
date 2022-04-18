package pl.pwr.edu.lab.kubik.financesmanager.db.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Instalment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer instalmentId;

    @ManyToOne
    @JoinColumn(name = "eventId")
    Event event;
    Integer noInstalment;
    String date;
    Integer amount;
}
