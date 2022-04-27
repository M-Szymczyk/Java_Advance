package pl.pwr.edu.lab.kubik.financesmanager.db.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@ToString
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventId;
    private String name, place;
    private Integer date;

    public Event() {}

    public Event(String name, String place, Integer date) {
        this.name = name;
        this.place = place;
        this.date = date;
    }
}
