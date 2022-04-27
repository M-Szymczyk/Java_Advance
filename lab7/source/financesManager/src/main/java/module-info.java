module financesManager {
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.sql;
    requires spring.tx;
    requires spring.context;
    requires spring.beans;
    requires lombok;
    requires java.persistence;
    requires spring.data.jpa;
    requires spring.data.commons;
    requires spring.boot;
    requires spring.boot.autoconfigure;

    exports pl.pwr.edu.lab.kubik.financesmanager.gui;
}