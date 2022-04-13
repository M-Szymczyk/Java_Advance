package pl.edu.pwr.bilbords.manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.edu.pwr.bilbords.factories.RMISSLClientSocketFactory;
import pl.edu.pwr.bilbords.factories.RMISSLServerSocketFactory;

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.Duration;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManagerController implements Initializable {
    static final int port = 2000;
    ObservableList<OrderTableElement> orderTableElementList = FXCollections.observableArrayList();
    ObservableList<BillboardTableElement> billboardTableElements = FXCollections.observableArrayList();
    public TableView<OrderTableElement> ordersTable;
    public TableView<BillboardTableElement> billboardsTable;
    public Button removeBtn, startBtn, stopBtn, setTimeBtn;
    Manager manager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // Create SSL-based registry
            Registry registry = LocateRegistry.createRegistry(port,
                    new RMISSLClientSocketFactory(),
                    new RMISSLServerSocketFactory());

//
//            // Bind this object instance to the name "HelloServer"
            manager =  new Manager(this);
            registry.bind("HelloServer",manager);

            System.out.println("HelloServer bound in registry");
        } catch (Exception e) {
            System.out.println("HelloImpl err: " + e.getMessage());
            e.printStackTrace();
        }
        TableColumn<OrderTableElement,String> OrderIdCol = new TableColumn<>("id");
        OrderIdCol.setPrefWidth(30);
        OrderIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<OrderTableElement,String> OrderTextCol = new TableColumn<>("text");
        OrderTextCol.setMinWidth(100);
        OrderTextCol.setCellValueFactory(new PropertyValueFactory<>("text"));
        TableColumn<OrderTableElement,String> OrderClientCol = new TableColumn<>("client");
        OrderClientCol.setMinWidth(100);
        OrderClientCol.setCellValueFactory(new PropertyValueFactory<>("client"));

        TableColumn<OrderTableElement,String> lastNameCol = new TableColumn<>("dur");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("dur"));
        ordersTable.getColumns().addAll(OrderIdCol,OrderTextCol,OrderClientCol,lastNameCol);
        ordersTable.setItems(orderTableElementList);


        TableColumn<BillboardTableElement,String> BillboardIdCol = new TableColumn<>("id");
        BillboardIdCol.setPrefWidth(30);
        BillboardIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<BillboardTableElement,String> BillboardCol = new TableColumn<>("billboard");
        BillboardCol.setMinWidth(100);
        BillboardCol.setCellValueFactory(new PropertyValueFactory<>("billboard"));

        billboardsTable.getColumns().addAll(BillboardIdCol,BillboardCol);
        billboardsTable.setItems(billboardTableElements);
    }

    public void removeBtnOnAction() {
        var delete = billboardsTable.getSelectionModel().getSelectedItem();

        manager.billboardMap.remove(Integer.valueOf(delete.getId()));
        billboardTableElements.remove(delete);
    }

    public void startBtnOnAction() throws RemoteException {
        var start = billboardsTable.getSelectionModel().getSelectedItem();
        manager.billboardMap.get(Integer.valueOf(start.getId())).start();
    }

    public void stopBtnOnAction() throws RemoteException {
        var start = billboardsTable.getSelectionModel().getSelectedItem();
        manager.billboardMap.get(Integer.valueOf(start.getId())).stop();
    }

    public void setTimeBtnOnAction() {
        var start = billboardsTable.getSelectionModel().getSelectedItem();
        TextInputDialog dialog = new TextInputDialog("5");
        dialog.setTitle("Text Input Dialog");
        dialog.setContentText("Please enter time:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(name -> {
            try {
                manager.billboardMap.get(Integer.valueOf(start.getId())).setDisplayInterval(Duration.ofMillis(Long.parseLong(name)));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

    }
}
