module pl.edu.pwr.kubik.encoder.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires signedlib;


    opens pl.edu.pwr.kubik.encoder.app to javafx.fxml;
    exports pl.edu.pwr.kubik.encoder.app;
}