package pl.edu.pwr.tkubik.serviceloader.impl01;

import com.google.auto.service.AutoService;

import pl.edu.pwr.tkubik.serviceloader.api.LogService;

@AutoService(LogService.class)
public class LogStdOut implements LogService {

    @Override
    public void log(String message) {
        System.out.println(message+"1");
    }
}
