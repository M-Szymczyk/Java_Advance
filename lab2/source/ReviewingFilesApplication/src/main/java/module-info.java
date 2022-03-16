module pl.edu.pwr.reviewingfilesapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    //exports pl.edu.pwr.reviewingfilesapplication.;
    exports pl.edu.pwr.reviewingfilesapplication.welcomeScene;
    opens pl.edu.pwr.reviewingfilesapplication.welcomeScene to javafx.fxml;
}