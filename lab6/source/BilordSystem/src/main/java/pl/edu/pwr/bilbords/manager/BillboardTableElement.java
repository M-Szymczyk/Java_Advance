package pl.edu.pwr.bilbords.manager;

import javafx.beans.property.SimpleStringProperty;

public class BillboardTableElement {
    SimpleStringProperty id, billboard;
    BillboardTableElement(String i, String billboard){
        id = new SimpleStringProperty(i);
        this.billboard = new SimpleStringProperty(billboard);
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

    public String getBillboard() {
        return billboard.get();
    }

    public SimpleStringProperty billboardProperty() {
        return billboard;
    }

    public void setBillboard(String billboard) {
        this.billboard.set(billboard);
    }
}
