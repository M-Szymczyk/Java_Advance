package pl.edu.pwr.bilbords.billboard;

import bilboards.IManager;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import pl.edu.pwr.bilbords.factories.RMISSLClientSocketFactory;

import java.net.InetAddress;
import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

public class BillboardController implements Initializable {
    final int port = 2000;
    public Label label;

    public void SetLabelText(String terxt){
        label.setText(terxt);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // Make reference to SSL-based registry

            Registry registry = LocateRegistry.getRegistry(
                    InetAddress.getLocalHost().getHostName(), port,
                    new RMISSLClientSocketFactory());

            // "obj" is the identifier that we'll use to refer
            // to the remote object that implements the "Hello"
            // interface
            IManager obj = (IManager) registry.lookup("HelloServer");
            Billboard client = new Billboard(this);
            client.setBillboardId(obj.bindBillboard(client));
            //obj.unbindBillboard(client.billboardId);
        } catch (Exception e) {
            System.out.println("HelloClient exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
