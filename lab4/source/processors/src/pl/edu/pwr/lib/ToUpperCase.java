package pl.edu.pwr.lib;

import pl.edu.pwr.processing.Processor;
import pl.edu.pwr.processing.SimulateProcessing;
import pl.edu.pwr.processing.StatusListener;

import java.util.concurrent.Executors;

public class ToUpperCase implements Processor {
    String result;
    private static int taskId=0;
    @Override
    public boolean submitTask(String task, StatusListener sl) {
        taskId++;
        SimulateProcessing simulateProcessing =  new SimulateProcessing(sl,taskId);
        java.util.concurrent.ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            while (true) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (simulateProcessing.getAi().get() >= 100) {
                    result = task.toUpperCase();
                    System.out.println("finished");
                    simulateProcessing.getExecutorService().shutdown();
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
