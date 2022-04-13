package pl.edu.pwr.bilbords.manager;

import javafx.beans.property.SimpleStringProperty;

public class OrderTableElement {
    SimpleStringProperty id, text, dur, client;

    public OrderTableElement(String id, String text, String dur, String client) {
        this.id = new SimpleStringProperty(id);
        this.text = new SimpleStringProperty(text);
        this.dur = new SimpleStringProperty(dur);
        this.client = new SimpleStringProperty(client);
    }


    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getText() {
        return text.get();
    }

    public SimpleStringProperty textProperty() {
        return text;
    }

    public void setText(String text) {
        this.text.set(text);
    }

    public String getDur() {
        return dur.get();
    }

    public SimpleStringProperty durProperty() {
        return dur;
    }

    public void setDur(String dur) {
        this.dur.set(dur);
    }

    public String getClient() {
        return client.get();
    }

    public SimpleStringProperty clientProperty() {
        return client;
    }

    public void setClient(String client) {
        this.client.set(client);
    }
}
