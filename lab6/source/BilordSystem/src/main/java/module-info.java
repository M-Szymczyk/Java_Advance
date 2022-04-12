module BilordSystem {
    requires javafx.graphics;
    requires java.rmi;
    requires javafx.fxml;

    exports bilboards;
    exports pl.edu.pwr.bilbords.gui.billboard;
    exports pl.edu.pwr.bilbords.gui.client;
    exports pl.edu.pwr.bilbords.gui.manager;
}