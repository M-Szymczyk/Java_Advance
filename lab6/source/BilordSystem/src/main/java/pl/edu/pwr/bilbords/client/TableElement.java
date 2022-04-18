package pl.edu.pwr.bilbords.client;

import bilboards.Order;
import javafx.beans.property.SimpleStringProperty;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TableElement {
    SimpleStringProperty id, text, dur;

    TableElement(String id,String string, Duration duration) {
        this.id = new SimpleStringProperty(id);
        text = new SimpleStringProperty(string);
        dur = new SimpleStringProperty(String.valueOf(duration.toSeconds()));
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
//
//    static List<TableElement> convert(Map<Integer, Order> map) {
//        List<TableElement> res = new ArrayList<>();
//        for (Order value : map.values()) {
//            res.add(new TableElement( value.advertText, value.displayPeriod));
//        }
//        return res;
//    }
}
