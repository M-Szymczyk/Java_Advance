module pl.edu.pwr.statistical.analysis {
    requires javafx.controls;
    requires javafx.fxml;


    opens pl.edu.pwr.statistical.analysis to javafx.fxml;
    exports pl.edu.pwr.statistical.analysis.gui;
    opens pl.edu.pwr.statistical.analysis.gui to javafx.fxml;
}