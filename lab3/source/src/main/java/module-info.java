module pl.pwr.edu.loaddatafromrestapi {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.context;
    requires java.desktop;
    requires java.net.http;
    requires json.simple;
    requires java.prefs;


    opens pl.pwr.edu.app.gui to javafx.fxml;
    exports pl.pwr.edu.app.gui;
//    exports pl.pwr.edu.loaddatafromrestapi.gui;
}