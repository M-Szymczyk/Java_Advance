module pl.edu.pwr.statisticalanalysis {
    requires javafx.controls;
    requires javafx.fxml;


    opens pl.edu.pwr.statisticalanalysis to javafx.fxml;
    exports pl.edu.pwr.statisticalanalysis;
}