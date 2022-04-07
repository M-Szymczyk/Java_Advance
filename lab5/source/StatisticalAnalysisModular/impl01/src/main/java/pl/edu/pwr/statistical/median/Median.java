package pl.edu.pwr.statistical.median;


import pl.edu.pwr.modular.ex.api.AnalysisException;
import pl.edu.pwr.modular.ex.api.AnalysisService;
import pl.edu.pwr.modular.ex.api.DataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;

public class Median implements AnalysisService {
    AtomicReference<DataSet> dataSet = new AtomicReference<>(), resultDataSet = new AtomicReference<>();
    Thread thread;

    @Override
    public void setOptions(String[] options) throws AnalysisException {

    }

    @Override
    public String getName() {
        return "Median";
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

            for (String s : dataSet.get().getHeader())
                list.add(new ArrayList<>());

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < dataSet.get().getData().length; i++)
                for (int j = 0; j < dataSet.get().getData()[i].length; j++)
                    list.get(j).add(Double.parseDouble(dataSet.get().getData()[i][j]));
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            resultDataSet.set(new DataSet());
            resultDataSet.get().setHeader(dataSet.get().getHeader());
            String[][] resultArray = new String[1][dataSet.get().getHeader().length];
            if (list.get(0).size() % 2 == 0) {
                for (int i = 0; i < dataSet.get().getHeader().length; i++) {
                    ArrayList<Double> columnList = list.get(i);
                    Double t1 = columnList.get(((columnList.size() - 1) / 2)), t2 = columnList.get((columnList.size() - 1) / 2 + 1), test = (t1 + t2) / 2;
                    resultArray[0][i] = String.valueOf(test);
                }
            } else {
                for (int i = 0; i < dataSet.get().getHeader().length; i++) {
                    ArrayList<Double> columnList = list.get(i);
                    resultArray[0][i] = String.valueOf(columnList.get((columnList.size() - 1) / 2 + 1));
                }
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
