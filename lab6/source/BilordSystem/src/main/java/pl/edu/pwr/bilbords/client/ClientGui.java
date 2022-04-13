package pl.edu.pwr.bilbords.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ClientGui extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientGui.class.getResource("client-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Client App");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        System.setProperty("javax.net.ssl.keyStore", "keys");
        System.setProperty("javax.net.ssl.keyStorePassword", "passphrase");
        System.setProperty("javax.net.ssl.trustStore", "keys");
        System.setProperty("javax.net.ssl.trustStorePassword", "passphrase");
        launch();
    }
}
