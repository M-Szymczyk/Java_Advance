package pl.edu.pwr.guiModular;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import pl.edu.pwr.modular.ex.api.DataSet;

import java.net.URL;
import java.util.ResourceBundle;

public class GuiController implements Initializable {
    DataSet dataSet = new DataSet();
    @FXML
    public TextArea textArea;
    public Button saveBtn, importBtn, importBtn11;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StringBuilder text = new StringBuilder();
        dataSet.setHeader(new String[]{"test", "test"});
        dataSet.setData(new String[][]{
                {"t", "2t"}, {"i", "m"}
        });
        if (dataSet.getHeader().length == dataSet.getData().length) {
            // todo jezeli liczba nagłówków nie równa się liczbe wierszy
        }
        int i = 0;
        for (String header : dataSet.getHeader()) {
            text.append(header).append("    ");
        }
        text.append("\n");
        for (String[] datum : dataSet.getData()) {
            for (String s : datum) {
                text.append(s).append("    ");
            }
            text.append("\n");
        }
        textArea.setText(text.toString());
    }
}
