package pl.edu.pwr.processing;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulateProcessing{
    AtomicInteger ai = new AtomicInteger(0);
    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    public SimulateProcessing(StatusListener sl, int taskId){

        executorService.scheduleAtFixedRate(
                ()->{
                    System.out.println("running"); // do debbugowania
                    ai.incrementAndGet();
                    sl.statusChanged(new Status(taskId,ai.get()));
                },
                1, 10, TimeUnit.MILLISECONDS);
    }

    public AtomicInteger getAi() {
        return ai;
    }

    public ScheduledExecutorService getExecutorService() {
        return executorService;
    }
}
