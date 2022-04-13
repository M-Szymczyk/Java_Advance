package pl.edu.pwr.bilbords.manager;

import bilboards.IBillboard;
import bilboards.IManager;
import bilboards.Order;
import pl.edu.pwr.bilbords.factories.RMISSLClientSocketFactory;
import pl.edu.pwr.bilbords.factories.RMISSLServerSocketFactory;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Manager extends UnicastRemoteObject implements IManager, Serializable {
    static final int port = 2000;
     final Map<Integer, Order> orders;
     final Map<Integer,IBillboard> billboardMap=new HashMap<>();
     ManagerController controller;
    int lastAddOrder = 0, lastAddedBillboard =0, maxNoOrders = 10, actualNoOrders = 0;

    public Manager(ManagerController controller) throws Exception {
        super(port, new RMISSLClientSocketFactory(), new RMISSLServerSocketFactory());
        this.controller = controller;
        orders = new HashMap<>();
    }

    @Override
    public int bindBillboard(IBillboard billboard) throws RemoteException {
        System.out.println("Receive billboard!");
        billboardMap.put(lastAddedBillboard++,billboard);
        maxNoOrders+=billboard.getCapacity()[0];
        controller.billboardTableElements.add(new BillboardTableElement(String.valueOf(lastAddedBillboard-1),String.valueOf(billboard.hashCode())));
        return lastAddedBillboard-1;
    }

    @Override
    public boolean unbindBillboard(int billboardId) throws RemoteException {
        var sth = billboardMap.get(billboardId).getCapacity();
        maxNoOrders-=sth[0];
        actualNoOrders-=sth[1];

        billboardMap.remove(billboardId);
        return true;
    }

    @Override
    public boolean placeOrder(Order order) throws RemoteException {
        System.out.println("Receive order!"+order.advertText);
        if (maxNoOrders < actualNoOrders)
            return false;

        controller.orderTableElementList.add(new OrderTableElement(String.valueOf(lastAddOrder),order.advertText,
                String.valueOf( order.displayPeriod.toSeconds()),String.valueOf(order.client.hashCode())));
        for (IBillboard value : billboardMap.values()) {
            if(value.getCapacity()[1]>0){
                value.addAdvertisement(order.advertText,order.displayPeriod,lastAddOrder);
                break;
            }
        }
        orders.put(lastAddOrder, order);
        order.client.setOrderId(lastAddOrder);
        lastAddOrder++;
        actualNoOrders++;
        return true;
    }

    @Override
    public boolean withdrawOrder(int orderId) throws RemoteException {
        return orders.remove(orderId) != null;
    }

//    public static void main(String[] args) {
//        // Create and install a security manager
//        if (System.getSecurityManager() == null) {
//            System.setSecurityManager(new SecurityManager());
//        }
//
//        try {
//            // Create SSL-based registry
//            Registry registry = LocateRegistry.createRegistry(port,
//                    new RMISSLClientSocketFactory(),
//                    new RMISSLServerSocketFactory());
//
////
////            // Bind this object instance to the name "HelloServer"
//            registry.bind("HelloServer", new Manager());
//
//            System.out.println("HelloServer bound in registry");
//        } catch (Exception e) {
//            System.out.println("HelloImpl err: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//




}
