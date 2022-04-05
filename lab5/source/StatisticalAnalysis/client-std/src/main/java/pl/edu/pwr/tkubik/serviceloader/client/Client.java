package pl.edu.pwr.tkubik.serviceloader.client;

import java.util.ServiceLoader;

import pl.edu.pwr.tkubik.serviceloader.api.LogService;

public class Client {

    public static void main(String[] args) {
        ServiceLoader<LogService> loader = ServiceLoader.load(LogService.class);
        System.out.println("Services loading done");
        for (LogService service : loader) {
            service.log("Log written by " + service.getClass());
        }
    }
}
