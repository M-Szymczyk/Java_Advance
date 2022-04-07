package pl.edu.pwr.statistical.average;

import pl.edu.pwr.modular.ex.api.AnalysisException;
import pl.edu.pwr.modular.ex.api.AnalysisService;
import pl.edu.pwr.modular.ex.api.DataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;

public class Average implements AnalysisService {
    AtomicReference<DataSet> dataSet = new AtomicReference<>(), resultDataSet = new AtomicReference<>();
    Thread thread;

    @Override
    public void setOptions(String[] options) throws AnalysisException {

    }

    @Override
    public String getName() {
        return "Average";
    }

    @Override
    public void submit(DataSet ds) throws AnalysisException {
        if (thread != null) {
            if (!thread.getState().equals(Thread.State.TERMINATED))
                throw new AnalysisException("working");
            else
                runProcessing(ds);
        } else {
            runProcessing(ds);
        }
    }

    private void runProcessing(DataSet ds) {
        thread = new Thread(() -> {

            dataSet.set(ds);
            ArrayList<ArrayList<Double>> list = new ArrayList<>();
            Double[] sum = new Double[ds.getHeader().length];
            for (int i = 0; i < dataSet.get().getHeader().length; i++) {
                list.add(new ArrayList<>());
                sum[i] = 0.0;
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < dataSet.get().getData().length; i++) {
                for (int j = 0; j < dataSet.get().getData()[i].length; j++) {
                    list.get(j).add(Double.parseDouble(dataSet.get().getData()[i][j]));
                    sum[j] += Double.parseDouble(dataSet.get().getData()[i][j]);
                }
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            resultDataSet.set(new DataSet());
            resultDataSet.get().setHeader(dataSet.get().getHeader());
            String[][] resultArray = new String[1][dataSet.get().getHeader().length];
            for (int i = 0; i < dataSet.get().getHeader().length; i++) {
                resultArray[0][i] = String.valueOf(sum[i] / dataSet.get().getData().length);
            }
            resultDataSet.get().setData(resultArray);
        });
        thread.start();
    }

    @Override
    public DataSet retrieve(boolean clear) {
        if (resultDataSet == null)
            return null;

        DataSet res = resultDataSet.get();
        if (clear)
            if(thread!=null)
                if(thread.getState().equals(Thread.State.TERMINATED)) {
                    dataSet = new AtomicReference<>();
                    resultDataSet = new AtomicReference<>();
                }

        return res;
    }
}
