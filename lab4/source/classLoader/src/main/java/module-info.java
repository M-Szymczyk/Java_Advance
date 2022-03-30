module pl.edu.pwr.classloader {
    requires javafx.controls;
    requires javafx.fxml;


    opens pl.edu.pwr.classloader to javafx.fxml;
    exports pl.edu.pwr.classloader;
}