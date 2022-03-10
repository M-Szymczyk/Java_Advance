package pl.edu.pwr.application;

import javafx.beans.property.SimpleStringProperty;

/**
 * Class required to list factory
 */
public class FileString {
    private final SimpleStringProperty fileName;
    private final SimpleStringProperty status;

    public FileString(String fileName, String status) {
        this.fileName = new SimpleStringProperty(fileName);
        this.status = new SimpleStringProperty(status);
    }

    public String getFileName() {
        return fileName.get();
    }

    public SimpleStringProperty fileNameProperty() {
        return fileName;
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public void setFileName(String fileName) {
        this.fileName.set(fileName);
    }
}
