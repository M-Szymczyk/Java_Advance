package pl.edu.pwr.processing;

import pl.edu.pwr.processing.Status;
import pl.edu.pwr.processing.StatusListener;

public class SomeStatusListener implements StatusListener {
    @Override
    public void statusChanged(Status s) {
        System.out.println("Progress:"+s.getProgress()+" TaskId:" +s.getTaskId());
    }
}
