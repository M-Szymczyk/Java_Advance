package pl.edu.pwr.bilbords.gui.manager;

import bilboards.IBillboard;
import bilboards.IManager;
import bilboards.Order;
import pl.edu.pwr.bilbords.gui.client.RMISSLClientSocketFactory;

import javax.net.ssl.SSLServerSocketFactory;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class Manager extends UnicastRemoteObject implements IManager, Serializable {
    static final int port = 2000;
    private final Map<Integer, Order> orders;
    Integer lastAddOrder = 0, maxNoOrders = 10, actualNoOrders = 0;

    public Manager() throws Exception {
        super(port, new RMISSLClientSocketFactory(), new RMISSLServerSocketFactory());
        orders = new HashMap<>();
    }

    @Override
    public int bindBillboard(IBillboard billboard) throws RemoteException {
        return 0;
    }

    @Override
    public boolean unbindBillboard(int billboardId) throws RemoteException {
        return false;
    }

    @Override
    public boolean placeOrder(Order order) throws RemoteException {
        System.out.println("Receive order!");
        if (maxNoOrders < actualNoOrders)
            return false;
        orders.put(lastAddOrder, order);
        order.client.setOrderId(lastAddOrder);
        lastAddOrder++;
        return true;
    }

    @Override
    public boolean withdrawOrder(int orderId) throws RemoteException {
        return orders.remove(orderId) != null;
    }

    public static void main(String[] args) {
        // Create and install a security manager
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try {
            // Create SSL-based registry
            Registry registry = LocateRegistry.createRegistry(port,
                    new RMISSLClientSocketFactory(),
                    new RMISSLServerSocketFactory());

//
//            // Bind this object instance to the name "HelloServer"
            registry.bind("HelloServer", new Manager());

            System.out.println("HelloServer bound in registry");
        } catch (Exception e) {
            System.out.println("HelloImpl err: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
