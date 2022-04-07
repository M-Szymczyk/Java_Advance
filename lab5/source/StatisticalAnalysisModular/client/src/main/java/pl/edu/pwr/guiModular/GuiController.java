package pl.edu.pwr.guiModular;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import pl.edu.pwr.modular.ex.api.AnalysisException;
import pl.edu.pwr.modular.ex.api.AnalysisService;
import pl.edu.pwr.modular.ex.api.DataSet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.ServiceLoader;

public class GuiController implements Initializable {
    ServiceLoader<AnalysisService> loader = ServiceLoader.load(AnalysisService.class);
    DataSet dataSet = new DataSet();
    @FXML
    public TextArea textArea;
    public CheckBox checkBoxDeleteData;
    public Button saveBtn, importBtn, runService, getResult;
    public ListView<String> listView;

    public void iniTListView() {
        ArrayList<String> listOfServices = new ArrayList<>();
        for (AnalysisService analysisService : loader)
            listOfServices.add(analysisService.getName());
        listView.setItems(FXCollections.observableList(listOfServices));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        iniTListView();
        readFile( new File("C:\\Users\\Hyperbook\\Desktop\\JavaZaw\\mszymczyk_248881_java\\lab5\\release\\datatest.txt"));

    }


    private void setTextArea() {
        StringBuilder text = new StringBuilder();
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

    public void importSetData() {
        FileChooser chooser = new FileChooser();
        File chosen;
       // if (!chosen.exists())
            chosen = chooser.showOpenDialog(null);
        if (chosen != null) {
            readFile(chosen);
        }
    }

    private void readFile(File file) {
        try (Scanner myReader = new Scanner(file)) {
            if (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split(" ");
                String[][] result = new String[(int) Files.lines(file.toPath()).count() - 1][data.length];
                dataSet.setHeader(data);
                int rowCounter = 0;
                while (myReader.hasNextLine()) {
                    data = myReader.nextLine().split(" ");
                    System.arraycopy(data, 0, result[rowCounter], 0, data.length);
                    rowCounter++;
                }
                dataSet.setData(result);
                setTextArea();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void runService() {
        try {
            String selected = getSelectedItem();
            for (AnalysisService analysisService : loader) {
                if (analysisService.getName().equals(selected)) {
                   // System.out.println("Subbmiting to: " + analysisService.getName());
                    try {
                        analysisService.submit(dataSet);
                    } catch (AnalysisException e) {
                        Dialog<String> dialog = new Dialog<>();
                        dialog.setTitle("Dialog");
                        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                        dialog.setContentText("Service still processing the data!");
                        dialog.getDialogPane().getButtonTypes().add(type);
                        dialog.show();
                    }
                }
            }
        } catch (Exception e) {

            noSelectedItemDialogError(e.getMessage());
        }
    }

    private String getSelectedItem() throws Exception {
        String res = listView.getSelectionModel().getSelectedItems().get(0);
        if (res == null)
            throw new Exception("No selected item from service list!");
        return res;
    }

    public void getResultOnAction() {
        try {
            String selected = getSelectedItem();

            for (AnalysisService analysisService : loader) {

                if (analysisService.getName().equals(selected)) {
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    DataSet res = analysisService.retrieve(checkBoxDeleteData.isSelected());
                    Dialog<String> dialog = new Dialog<>();
                    dialog.setTitle("Service " + listView.getSelectionModel().getSelectedItems().get(0) + " result");
                    ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    if (res == null) {
                        dialog.setContentText("brak danych");
                    } else {
                        StringBuilder textToDisplay = new StringBuilder();
                        for (int i = 0; i < dataSet.getHeader().length; i++)
                            textToDisplay.append(dataSet.getHeader()[i]).append(", ");
                        textToDisplay.append("\n");
                        for (int i = 0; i < dataSet.getHeader().length; i++)
                            textToDisplay.append(res.getData()[0][i]).append(", ");
                        dialog.setContentText(textToDisplay.toString());
                    }
                    dialog.getDialogPane().getButtonTypes().add(type);
                    dialog.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            noSelectedItemDialogError(e.getMessage());
        }
    }

    private void noSelectedItemDialogError(String errorMessage) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Error");
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.setContentText(errorMessage);
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.show();
    }
}
