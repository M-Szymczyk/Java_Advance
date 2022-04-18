package pl.edu.pwr.bilbords.client;

import bilboards.IClient;
import bilboards.IManager;
import bilboards.Order;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client extends UnicastRemoteObject implements IClient, Serializable {
    static final int port = 2000;
    public final Map<Integer, Order> clientOrders = new HashMap<>();
    private Order lastAddedOrder;
    public final List<Integer> listOfOrders = new ArrayList<>();
    private final ClientController controller;
    protected Client(ClientController controller) throws RemoteException {
        super();
        this.controller = controller;
    }

    @Override
    public void setOrderId(int orderId) throws RemoteException {
        clientOrders.put(orderId, lastAddedOrder);
        controller.elementObservableList.add(new TableElement(String.valueOf(orderId),lastAddedOrder.advertText,lastAddedOrder.displayPeriod));
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
}

