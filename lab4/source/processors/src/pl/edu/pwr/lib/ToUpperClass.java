package pl.edu.pwr.lib;

import pl.edu.pwr.processing.Processor;
import pl.edu.pwr.processing.Status;
import pl.edu.pwr.processing.StatusListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ToUpperClass implements Processor {
    String result;
    private static int taskId=0;
    @Override
    public boolean submitTask(String task, StatusListener sl) {
        taskId++;
        AtomicInteger ai = new AtomicInteger(0);
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(
                ()->{
                    System.out.println("running"); // do debbugowania
                    ai.incrementAndGet();
                    sl.statusChanged(new Status(taskId,ai.get()));
                },
                1, 10, TimeUnit.MILLISECONDS);
        java.util.concurrent.ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            while (true) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (ai.get() >= 100) {
                    result = task.toUpperCase();
                    System.out.println("finished");
                    executorService.shutdown();
                    executor.shutdown();
                    break;
                }
            }
        });
        return false;
    }

    @Override
    public String getInfo() {
        return "Klasa zamienia podany ciąg znaków na duże";
    }

    @Override
    public String getResult() {
        return result;
    }
}
