module pl.edu.pwr.kubik.encoder.app {
    requires javafx.controls;
    requires javafx.fxml;


    opens pl.edu.pwr.kubik.encoder.app to javafx.fxml;
    exports pl.edu.pwr.kubik.encoder.app;
}