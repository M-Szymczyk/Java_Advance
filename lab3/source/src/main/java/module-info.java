module pl.pwr.edu.loaddatafromrestapi {
    requires javafx.controls;
    requires javafx.fxml;


    opens pl.pwr.edu.loaddatafromrestapi to javafx.fxml;
    exports pl.pwr.edu.loaddatafromrestapi;
}