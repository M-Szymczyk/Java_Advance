package pl.pwr.edu.lab.kubik.financesmanager.gui.tables;

import javafx.beans.property.SimpleStringProperty;
import lombok.Getter;


public class EventTable {
    SimpleStringProperty eventId, name, place, date;

    public EventTable(String eventId, String name, String place, String date) {
        this.eventId = new SimpleStringProperty(eventId);
        this.name = new SimpleStringProperty(name);
        this.place = new SimpleStringProperty(place);
        this.date = new SimpleStringProperty(date);
    }

    public String getEventId() {
        return eventId.get();
    }

    public SimpleStringProperty eventIdProperty() {
        return eventId;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getPlace() {
        return place.get();
    }

    public SimpleStringProperty placeProperty() {
        return place;
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }
}
