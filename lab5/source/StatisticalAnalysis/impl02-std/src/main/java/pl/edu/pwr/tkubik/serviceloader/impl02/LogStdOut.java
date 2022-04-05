package pl.edu.pwr.tkubik.serviceloader.impl02;

import pl.edu.pwr.tkubik.serviceloader.api.LogService;

public class LogStdOut implements LogService {

    @Override
    public void log(String message) {
        System.out.println(message+"2");
    }
}
