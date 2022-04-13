module BilordSystem {
    requires javafx.graphics;
    requires java.rmi;
    requires javafx.fxml;
    requires javafx.controls;

    exports bilboards;
    exports pl.edu.pwr.bilbords.billboard;
    exports pl.edu.pwr.bilbords.client;
    exports pl.edu.pwr.bilbords.manager;
    exports pl.edu.pwr.bilbords.factories;
}