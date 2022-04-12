package pl.edu.pwr.bilbords.gui.client;

import bilboards.IClient;
import bilboards.IManager;
import bilboards.Order;

import java.rmi.server.UnicastRemoteObject;
import java.time.Duration;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIClientSocketFactory;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client extends UnicastRemoteObject implements IClient, Serializable {
    static final int port = 2000;
    private final Map<Integer, Order> clientOrders = new HashMap<>();
    private Order lastAddedOrder;
    public final List<Integer> listOfOrders = new ArrayList<>();

    protected Client() throws RemoteException {
    }

    @Override
    public void setOrderId(int orderId) throws RemoteException {
        clientOrders.put(orderId, lastAddedOrder);
        listOfOrders.add(orderId);
        lastAddedOrder = null;
    }

    public int hashCode() {
        return getClass().hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else return obj != null && getClass() == obj.getClass();
    }

    public void createOrder(String text, Duration duration, IManager manager) throws Exception {
        if (lastAddedOrder != null)
            throw new Exception("Trwa przetwarzanie poprzedniego zamówienia");
        Order order = new Order();
        order.client = this;
        order.advertText = text;
        order.displayPeriod = duration;
        lastAddedOrder = order;
        if (!manager.placeOrder(order)) {
            lastAddedOrder = null;
            throw new Exception("Zadanie nie zostało przyjęte!");
        }
    }
    public  void withdrawOrder(int orderId,IManager manager) throws RemoteException {
        System.out.println(manager.withdrawOrder(orderId));
        clientOrders.remove(orderId);
        listOfOrders.remove(orderId);
    }


    public static void main(String[] args) {
        try {
            // Make reference to SSL-based registry
            System.setProperty("javax.net.ssl.keyStore", "keys");
            System.setProperty("javax.net.ssl.keyStorePassword", "passphrase");
            System.setProperty("javax.net.ssl.trustStore", "keys");
            System.setProperty("javax.net.ssl.trustStorePassword", "passphrase");
            Registry registry = LocateRegistry.getRegistry(
                    InetAddress.getLocalHost().getHostName(), port,
                    new RMISSLClientSocketFactory());

            // "obj" is the identifier that we'll use to refer
            // to the remote object that implements the "Hello"
            // interface
            IManager obj = (IManager) registry.lookup("HelloServer");
            Client client = new Client();
            client.createOrder("Daniel potrafi w wzorce",Duration.ofMillis(100),obj);
            for (Integer listOfOrder : client.listOfOrders) {
                System.out.println(listOfOrder+",");
            }
            client.withdrawOrder(0,obj);

        } catch (Exception e) {
            System.out.println("HelloClient exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

