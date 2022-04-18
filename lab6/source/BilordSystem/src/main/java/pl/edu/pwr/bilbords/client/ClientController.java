package pl.edu.pwr.bilbords.client;

import bilboards.IManager;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import pl.edu.pwr.bilbords.factories.RMISSLClientSocketFactory;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.Duration;
import java.util.*;

public class ClientController implements Initializable {
    @FXML
    public Button createOrderBtn,removeOrderBtn;
    public TableView<TableElement> tableOrders;
    public ObservableList<TableElement> elementObservableList = FXCollections.observableArrayList();
    Registry registry;
    IManager manager;
    Client client ;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            registry = LocateRegistry.getRegistry(
                    InetAddress.getLocalHost().getHostName(), Client.port,
                    new RMISSLClientSocketFactory());
            manager = (IManager) registry.lookup("HelloServer");
            client = new Client(this);
        } catch (RemoteException | NotBoundException | UnknownHostException e) {
            e.printStackTrace();
        }

        TableColumn<TableElement,String> idCol = new TableColumn<>("id");
        idCol.setMinWidth(30);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<TableElement,String> OrderTextCol = new TableColumn<>("text");
        OrderTextCol.setMinWidth(100);
        OrderTextCol.setCellValueFactory(new PropertyValueFactory<>("text"));

        TableColumn<TableElement,String> lastNameCol = new TableColumn<>("dur");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("dur"));
        tableOrders.getColumns().addAll(idCol,OrderTextCol,lastNameCol);
            tableOrders.setItems(elementObservableList);

    }

    public void setId(int id){

    }

    public void createOrderBtnOnAction() {
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Login Dialog");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("text to display");
        TextField password = new TextField();
        password.setPromptText("duration");

        grid.add(new Label("Text:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Duration:"), 0, 1);
        grid.add(password, 1, 1);

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(username::requestFocus);

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            try {
                client.createOrder(usernamePassword.getKey() , Duration.ofSeconds(Integer.parseInt(usernamePassword.getValue())),manager);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("Username=" +usernamePassword.getKey() + ", Password=" +usernamePassword.getValue() );
        });
    }

    public void removeOrderBtnOnAction() {

        tableOrders.getSelectionModel().getSelectedItem();
        //todo
    }
}
//
//            client.createOrder("Daniel potrafi w wzorce",Duration.ofMillis(100),obj);
//                    for (Integer listOfOrder : client.listOfOrders) {
//                    System.out.println(listOfOrder+",");
//                    }
//                    client.withdrawOrder(0,obj);