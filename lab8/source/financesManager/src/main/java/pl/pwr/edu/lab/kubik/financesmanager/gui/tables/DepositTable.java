package pl.pwr.edu.lab.kubik.financesmanager.gui.tables;

import javafx.beans.property.SimpleStringProperty;
import lombok.Getter;


public class DepositTable {
    SimpleStringProperty depositId, date, amount, personId, eventId, installmentId, noPayment;

    public DepositTable(String depositId, String date, String amount,
                        String personId, String eventId,
                        String installmentId, String noPayment) {
        this.depositId = new SimpleStringProperty(depositId);
        this.date = new SimpleStringProperty(date);
        this.amount = new SimpleStringProperty(amount);
        this.personId = new SimpleStringProperty(personId);
        this.eventId = new SimpleStringProperty(eventId);
        this.installmentId = new SimpleStringProperty(installmentId);
        this.noPayment = new SimpleStringProperty(noPayment);
    }

    public String getDepositId() {
        return depositId.get();
    }

    public SimpleStringProperty depositIdProperty() {
        return depositId;
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

    public String getPersonId() {
        return personId.get();
    }

    public SimpleStringProperty personIdProperty() {
        return personId;
    }

    public String getEventId() {
        return eventId.get();
    }

    public SimpleStringProperty eventIdProperty() {
        return eventId;
    }

    public String getInstallmentId() {
        return installmentId.get();
    }

    public SimpleStringProperty installmentIdProperty() {
        return installmentId;
    }

    public String getNoPayment() {
        return noPayment.get();
    }

    public SimpleStringProperty noPaymentProperty() {
        return noPayment;
    }
}
