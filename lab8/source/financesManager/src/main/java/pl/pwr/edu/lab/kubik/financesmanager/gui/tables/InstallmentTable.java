package pl.pwr.edu.lab.kubik.financesmanager.gui.tables;

import javafx.beans.property.SimpleStringProperty;
import lombok.Getter;


public class InstallmentTable {
    SimpleStringProperty installmentId, eventId, number, date, amount;

    public InstallmentTable(String installmentId, String eventId,
                            String number, String date, String amount) {
        this.installmentId = new SimpleStringProperty(installmentId);
        this.eventId = new SimpleStringProperty(eventId);
        this.number = new SimpleStringProperty(number);
        this.date = new SimpleStringProperty(date);
        this.amount = new SimpleStringProperty(amount);
    }

    public String getInstallmentId() {
        return installmentId.get();
    }

    public SimpleStringProperty installmentIdProperty() {
        return installmentId;
    }

    public String getEventId() {
        return eventId.get();
    }

    public SimpleStringProperty eventIdProperty() {
        return eventId;
    }

    public String getNumber() {
        return number.get();
    }

    public SimpleStringProperty numberProperty() {
        return number;
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public String getAmount() {
        return amount.get();
    }

    public SimpleStringProperty amountProperty() {
        return amount;
    }
}
