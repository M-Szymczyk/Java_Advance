package pl.edu.pwr.bilbords.billboard;

import bilboards.IBillboard;
import javafx.application.Platform;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

class AdvertisementText{
    String text;
    Duration duration;

    public AdvertisementText(String text, Duration duration) {
        this.text = text;
        this.duration = duration;
    }
}

public class Billboard extends UnicastRemoteObject implements IBillboard, Serializable {
    int billboardId, textCapacity=10,textFree=10;
    Duration duration = Duration.ofSeconds(2);
    AtomicReference<Map<Integer,AdvertisementText>> refToListOfTexts = new AtomicReference<>();
    Thread thread;
    static final int port = 2000;

    BillboardController controller;



    public Billboard(  BillboardController controller) throws RemoteException {
        super();
        this.controller = controller;
        refToListOfTexts.set(new HashMap<>());
    }

    public void setBillboardId(int billboardId) {
        this.billboardId = billboardId;
    }

    @Override
    public boolean addAdvertisement(String advertText, Duration displayPeriod, int orderId)
            throws RemoteException {
        if(textCapacity>textFree)
            return false;
        textFree++;
        refToListOfTexts.get().put( orderId,new AdvertisementText(advertText,displayPeriod));
        return true;
    }

    @Override
    public boolean removeAdvertisement(int orderId) throws RemoteException {
        if(!refToListOfTexts.get().containsKey(orderId))
            return false;
        refToListOfTexts.get().remove(orderId);
        return false;
    }

    @Override
    public int[] getCapacity() throws RemoteException {
        return new int[]{textCapacity,textFree};
    }

    @Override
    public void setDisplayInterval(Duration displayInterval) throws RemoteException {
        duration = displayInterval;

    }

    @Override
    public boolean start() throws RemoteException {
        thread =   new Thread(()->{
            Queue<AdvertisementText> list = new LinkedList<>(refToListOfTexts.get().values());
            while (true){
                for (AdvertisementText advertisementText : list) {
                    if(advertisementText.duration.toMillis()<Duration.ZERO.toMillis()) {
                        list.remove(advertisementText);
                        break;
                    }
                    Platform.runLater(() -> controller.SetLabelText(advertisementText.text+advertisementText.duration.toSeconds()));
                    advertisementText.duration = advertisementText.duration.minus(duration);
                    try {
                        Thread.sleep(duration.toMillis());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


                if(list.isEmpty()) {
                    Platform.runLater(() -> controller.SetLabelText("empty"));
                    break;
                }
            }
        });
        thread.start();
        return false;
    }

    @Override
    public boolean stop() throws RemoteException {
        //todo implement
        return false;
    }

}
