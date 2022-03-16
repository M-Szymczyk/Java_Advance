package pl.edu.pwr.reviewingfilesapplication.welcomeScene;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import pl.edu.pwr.reviewingfilesapplication.person.PersonData;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Objects;


public class WelcomeScene extends Application {
    HashMap<String, WeakReference<PersonData>> weakHashMap = new HashMap<>();
    PersonData chosenPerson;
    TextField nameTextField, surnameTextField, ageTextField, uniTextField;
    Label nameLabel, surnameLabel, ageLabel, uniLabel, readInfoLabel;
    ImageView image;
    Button nextBtn = new Button("Następny"), prefBtn = new Button("Poprzedni");
    CheckBoxTreeItem<String> selectedItem;
    File chosenDir;

    @Override
    public void start(Stage stage) {
        /// choose directory
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File baseFile = new File("C:\\Users\\Hyperbook\\Desktop\\JavaZaw\\mszymczyk_248881_java\\lab2\\release");
        if (baseFile.exists())
            directoryChooser.setInitialDirectory(baseFile);
        chosenDir = directoryChooser.showDialog(stage);

        Pane pane = new Pane();
        pane.setPrefWidth(850);
        pane.setPrefHeight(400);

        TreeView<String> treeView = new TreeView<>();
        treeView.setRoot(getNodesForDirectory(chosenDir));
        treeView.getSelectionModel().selectedItemProperty().addListener((ChangeListener) (observable, oldValue, newValue) -> {
            selectedItem = (CheckBoxTreeItem<String>) newValue;
            refreshDisplayedData();
        });

        initTextFields();
        setTextFields(chosenPerson);
        Button btn = new Button("Garbage Collector");
        btn.setLayoutY(350);
        btn.setLayoutX(350);
        btn.setOnAction(event ->System.gc());
        pane.getChildren().add(btn);
        pane.getChildren().addAll(treeView, image, nameTextField, surnameTextField, ageTextField, uniTextField,
                nameLabel, surnameLabel, ageLabel, uniLabel,/* nextBtn, prefBtn,*/ readInfoLabel);
        stage.setScene(new Scene(pane));
        stage.setTitle("Application");
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Method refresh displayed data
     */
    void refreshDisplayedData() {
        try {
            WeakReference<PersonData> personDataWeakReference = weakHashMap.get(selectedItem.getValue());
            if (personDataWeakReference == null) {
                PersonData personData = PersonData.readFromRecordFile(new File(chosenDir.toString() + "\\" + selectedItem.getValue()).toPath());
                weakHashMap.put(selectedItem.getValue(), new WeakReference<>(personData));
                readInfoLabel.setTextFill(Color.color(1, 0, 0));
                readInfoLabel.setText("Wczytano z pliku");
                setTextFields(personData);
            } else {
                if (personDataWeakReference.get() != null) {
                    readInfoLabel.setTextFill(Color.color(0, 1, 0));
                    readInfoLabel.setText("Wczytano z pamięci");
                    setTextFields(personDataWeakReference.get());
                } else {
                    PersonData personData = PersonData.readFromRecordFile(new File(chosenDir.toString() + "\\" + selectedItem.getValue()).toPath());
                    weakHashMap.put(selectedItem.getValue(), new WeakReference<>(personData));
                    readInfoLabel.setTextFill(Color.color(1, 0, 0));
                    readInfoLabel.setText("Wczytano z pliku");
                    setTextFields(personData);
                }
            }

        } catch (IOException e) {
            //e.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Wybrano katalog nie zawierający\n pliku record.txt oraz image.png!");
            a.show();
        }
    }

    /**
     * Method init textFields and textLabels
     */
    private void initTextFields() {
        nextBtn.setLayoutX(330);
        nextBtn.setLayoutY(350);
        prefBtn.setLayoutX(260);
        prefBtn.setLayoutY(350);

        nextBtn.setOnAction(event -> {
            if (selectedItem.nextSibling() != null)
                selectedItem = (CheckBoxTreeItem<String>) selectedItem.nextSibling();
            //selectedItem.setSelected(true);
            refreshDisplayedData();
        });
        nextBtn.setOnAction(event -> {
            if (selectedItem.previousSibling() != null)
                selectedItem = (CheckBoxTreeItem<String>) selectedItem.previousSibling();
            //selectedItem.setSelected(true);

            refreshDisplayedData();

        });

        image = new ImageView();
        image.setX(650);
        image.setY(10);
        image.setFitWidth(150);
        image.setPreserveRatio(true);

        readInfoLabel = new Label();
        readInfoLabel.setLayoutX(560);
        readInfoLabel.setLayoutY(350);
        readInfoLabel.setTextFill(Color.color(1, 0, 0));
        readInfoLabel.setFont(new Font(20.0));
        readInfoLabel.setText("Wczytano z pliku");
        int labelX = 260, text = 330;
        nameTextField = new TextField();
        surnameTextField = new TextField();
        ageTextField = new TextField();
        uniTextField = new TextField();
        nameTextField.setEditable(false);
        surnameTextField.setEditable(false);
        ageTextField.setEditable(false);
        uniTextField.setEditable(false);
        nameTextField.setLayoutX(text);
        nameTextField.setLayoutY(20);
        surnameTextField.setLayoutX(text);
        surnameTextField.setLayoutY(50);
        ageTextField.setLayoutX(text);
        ageTextField.setLayoutY(80);
        uniTextField.setLayoutX(text);
        uniTextField.setLayoutY(110);

        nameLabel = new Label();
        surnameLabel = new Label();
        ageLabel = new Label();
        uniLabel = new Label();
        nameLabel.setLayoutX(labelX);
        surnameLabel.setLayoutX(labelX);
        ageLabel.setLayoutX(labelX);
        uniLabel.setLayoutX(labelX);
        nameLabel.setLayoutY(20);
        surnameLabel.setLayoutY(50);
        ageLabel.setLayoutY(80);
        uniLabel.setLayoutY(110);
        nameLabel.setText("Imię:");
        surnameLabel.setText("Nazwisko:");
        ageLabel.setText("Wiek:");
        uniLabel.setText("Uczelnia:");
    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * Method add tree of subdirectories to TreeView
     *
     * @param directory base directory
     * @return root of tree
     */
    public CheckBoxTreeItem<String> getNodesForDirectory(File directory) {
        CheckBoxTreeItem<String> root = new CheckBoxTreeItem<>(directory.getName());
        root.setExpanded(true);
        for (File f : Objects.requireNonNull(directory.listFiles())) {
            if (f.isDirectory()) {
                CheckBoxTreeItem<String> treeItem = new CheckBoxTreeItem<>(f.getName());
                if (chosenPerson == null) {
                    selectedItem = treeItem;
                    try {
                        chosenPerson = PersonData.readFromRecordFile(new File(chosenDir.toString() + "\\" +
                                selectedItem.getValue()).toPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                root.getChildren().add(treeItem);
            }
        }
        return root;
    }

    /**
     * Method set text to TextFields
     *
     * @param personData class which contain all information
     */
    void setTextFields(PersonData personData) {
        nameTextField.setText(personData.name);
        surnameTextField.setText(personData.surname);
        ageTextField.setText(String.valueOf(personData.age));
        uniTextField.setText(personData.university);
        this.image.setImage(personData.myPicture);
    }
}
